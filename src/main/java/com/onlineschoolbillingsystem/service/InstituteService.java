package com.onlineschoolbillingsystem.service;

import com.onlineschoolbillingsystem.entity.Institute;
import com.onlineschoolbillingsystem.entity.InstituteClass;
import com.onlineschoolbillingsystem.entity.MonthlyBill;
import com.onlineschoolbillingsystem.entity.Student;
import com.onlineschoolbillingsystem.repository.InstituteClassRepository;
import com.onlineschoolbillingsystem.repository.InstituteRepository;
import com.onlineschoolbillingsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class handles all the functions related to an Institute.
 */
@Service
@Transactional
public class InstituteService implements UserDetailsService {

    private InstituteRepository instituteRepository;
    private InstituteClassRepository instituteClassRepository;
    private StudentRepository studentRepository;

    @Autowired
    public InstituteService(InstituteRepository instituteRepository, InstituteClassRepository instituteClassRepository, StudentRepository studentRepository) {
        this.instituteRepository = instituteRepository;
        this.instituteClassRepository = instituteClassRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s.equals("superuser@osbs.lk")){ // Super user
            return new User("superuser@osbs.lk", "superuser@osbs123", Collections.emptyList());
        }
        Institute instituteByEmail = instituteRepository.findInstituteByEmail(s);
        return new User(instituteByEmail.getEmail(), new String(instituteByEmail.getPassword()), Collections.emptyList());
    }

    public List<Institute> getAllInstitutes() {
        return instituteRepository.findAll();
    }

    public Institute registerInstitute(String email, char[] password, String address, String contact) {
        return instituteRepository.save(new Institute(email, password, address, contact));
    }

    public Optional<Institute> getInstituteById(long instituteId) {
        return instituteRepository.findById(instituteId);
    }

    public InstituteClass createClass(String subject, String day, String time, String lectureHall, String instructor, BigDecimal monthlyFee, Institute institute){
        return instituteClassRepository.save(new InstituteClass(subject, day, time, lectureHall, instructor, monthlyFee, institute));
    }

    /**
     * This method calculates the monthly bill for a student
     * @param instituteId - id of the Institute for which monthly bill is to be calculated for all classes undertaken by student at the institute
     * @param studentId - id of the Student for which monthly bill is to be calculated for all classes at an institute undertaken by the student
     * @return monthly bill
     */
    public MonthlyBill calculateMonthlyBillForStudent(long instituteId, long studentId) throws Exception {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new Exception(String.format("Student not found for ID: %s.", studentId)));

        Set<InstituteClass> enrolledClassesAtSpecificInstitute = student.getRegisteredClasses().stream().filter(instituteClass -> instituteClass.getInstitute().getInstituteId() == instituteId).collect(Collectors.toSet());
        Map<String, BigDecimal> feeBreakdown = new HashMap<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (InstituteClass enrolledClass : enrolledClassesAtSpecificInstitute) {
            feeBreakdown.put(enrolledClass.getSubject(), enrolledClass.getMonthlyFee()); // Add entry to monthly bill with subject of class and monthly fee
            grandTotal = grandTotal.add(enrolledClass.getMonthlyFee()); // Add monthly fee of the class to the grand total
        }

        return new MonthlyBill(studentId, student.getName(), feeBreakdown, grandTotal);
    }

    public Set<InstituteClass> getAllClassesByInstitute(long instituteId) {
        return instituteClassRepository.getAllByInstituteInstituteId(instituteId);
    }

    public Institute getInstituteByEmail(String email) {
        return instituteRepository.findInstituteByEmail(email);
    }
}

package com.onlineschoolbillingsystem;

import com.onlineschoolbillingsystem.entity.Institute;
import com.onlineschoolbillingsystem.entity.InstituteClass;
import com.onlineschoolbillingsystem.entity.Student;
import com.onlineschoolbillingsystem.repository.InstituteClassRepository;
import com.onlineschoolbillingsystem.repository.InstituteRepository;
import com.onlineschoolbillingsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class OnlineSchoolBillingSystem {

    private InstituteRepository instituteRepository;
    private InstituteClassRepository instituteClassRepository;
    private StudentRepository studentRepository;

    @Autowired
    public OnlineSchoolBillingSystem(InstituteRepository instituteRepository, InstituteClassRepository instituteClassRepository, StudentRepository studentRepository) {
        this.instituteRepository = instituteRepository;
        this.instituteClassRepository = instituteClassRepository;
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineSchoolBillingSystem.class, args);
    }

    @PostConstruct
    public void seedData() { // Comment this method if not needed to seed data into database on application startup

        Institute abc = new Institute(1, "admin@abcinstitute.lk", "admin@abcinstitute123".toCharArray(), "Colombo", "0771234567");
        Institute alpha = new Institute(2, "admin@alphainstitute.lk", "admin@alpha123".toCharArray(), "Gampaha", "0771234567");
        Institute xyz = new Institute(3, "admin@xyzinstitute.lk", "admin@xyz123".toCharArray(), "Kalutara", "0771234567");

        InstituteClass abcComSci = new InstituteClass("Computer Science", "Wednesday", "4.00PM-6.00PM", "A", "John", BigDecimal.valueOf(1500), abc);
        InstituteClass abcMaths = new InstituteClass("Bio", "Friday", "4.00PM-6.00PM", "A", "John", BigDecimal.valueOf(1500), abc);
        InstituteClass alphaBio = new InstituteClass("Maths", "Wednesday", "4.00PM-6.00PM", "A", "Adam", BigDecimal.valueOf(1500), alpha);

        Student jane = new Student("Jane", "Colombo", "0771234567", "FEMALE");
        Student scott = new Student("Scott", "Colombo", "0771234567", "MALE");
        List<Institute> seedInstitutes = Stream.of(abc, alpha, xyz).collect(Collectors.toList()); // Add more institutes to this list if needed later
        instituteRepository.saveAll(seedInstitutes);

        List<InstituteClass> seedClasses = Stream.of(abcComSci, abcMaths, alphaBio).collect(Collectors.toList()); // Add more classes to this list if needed later
        instituteClassRepository.saveAll(seedClasses);

        // Enroll seed students in seed classes
        List<Student> seedStudents = Stream.of(jane, scott).collect(Collectors.toList()); // Add more students to this list if needed later
        jane.getRegisteredClasses().add(abcComSci);
        jane.getRegisteredClasses().add(abcMaths);
        scott.getRegisteredClasses().add(alphaBio);

        studentRepository.saveAll(seedStudents);
    }

}

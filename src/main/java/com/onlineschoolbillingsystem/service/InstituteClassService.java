package com.onlineschoolbillingsystem.service;

import com.onlineschoolbillingsystem.entity.InstituteClass;
import com.onlineschoolbillingsystem.entity.Student;
import com.onlineschoolbillingsystem.repository.InstituteClassRepository;
import com.onlineschoolbillingsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class InstituteClassService {

    private InstituteClassRepository instituteClassRepository;
    private StudentRepository studentRepository;

    @Autowired
    public InstituteClassService(InstituteClassRepository instituteClassRepository, StudentRepository studentRepository) {
        this.instituteClassRepository = instituteClassRepository;
        this.studentRepository = studentRepository;
    }

    public Set<InstituteClass> enrollStudentInClass(long studentId, long classId) throws Exception {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new Exception(String.format("Student not found for ID: %s.", studentId)));
        InstituteClass instituteClass = instituteClassRepository.findById(classId).orElseThrow(() -> new Exception(String.format("Class not found for ID: %s.", classId)));
        instituteClass.getEnrolledStudents().add(student);

        Set<InstituteClass> updatedListOfRegisteredClassesForStudent = student.getRegisteredClasses();
        updatedListOfRegisteredClassesForStudent.add(instituteClass);

        return updatedListOfRegisteredClassesForStudent;
    }

    public Set<Student> getStudentsByClass(long classId) {
        return instituteClassRepository.findById(classId).orElse(new InstituteClass()).getEnrolledStudents();
    }
}

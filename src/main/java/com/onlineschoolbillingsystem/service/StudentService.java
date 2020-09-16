package com.onlineschoolbillingsystem.service;

import com.onlineschoolbillingsystem.entity.Student;
import com.onlineschoolbillingsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudentById(long studentId) {
        return studentRepository.findById(studentId);
    }

    public Student registerStudent(String name, String address, String contact, String gender) {
        return studentRepository.save(new Student(name, address, contact, gender));
    }

}

package com.onlineschoolbillingsystem.repository;

import com.onlineschoolbillingsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository interface for student.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}

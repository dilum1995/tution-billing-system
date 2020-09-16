package com.onlineschoolbillingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student {

    /**
     * This class represents a Student who attends the many Class that takes place in an Institute
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;
    private String name;
    private String address;
    private String contact;
    private String gender;
    private LocalDateTime registeredDate;

    @ManyToMany
    @JoinTable(name="InstituteClassStudent",
            joinColumns=@JoinColumn(name="student_id"),
            inverseJoinColumns=@JoinColumn(name="class_id "))
    private Set<InstituteClass> registeredClasses;

    public Student(String name, String address, String contact, String gender) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.registeredDate = LocalDateTime.now();
        this.registeredClasses = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}

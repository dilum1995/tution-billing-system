package com.onlineschoolbillingsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "InstituteClass")
public class InstituteClass {

    /**
     * This class represents the Class in an Institute (e.g: Biology, Maths, Accounts, etc.)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long classId;
    private String subject;
    private String day;
    private String time;
    private String lectureHall;
    private String instructor;
    private BigDecimal monthlyFee;

    @ManyToOne
    private Institute institute;

    @ManyToMany
    @JoinTable(name="InstituteClassStudent",
            joinColumns=@JoinColumn(name="class_id"),
            inverseJoinColumns=@JoinColumn(name="student_id "))
    @JsonBackReference // To avoid recursion issue when building JSON response
    private Set<Student> enrolledStudents;

    public InstituteClass(String subject, String day, String time, String lectureHall, String instructor, BigDecimal monthlyFee, Institute institute) {
        this.subject = subject;
        this.day = day;
        this.time = time;
        this.lectureHall = lectureHall;
        this.instructor = instructor;
        this.monthlyFee = monthlyFee;
        this.institute = institute;
        this.enrolledStudents = new HashSet<>();
    }
}

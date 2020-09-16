package com.onlineschoolbillingsystem.repository;

import com.onlineschoolbillingsystem.entity.InstituteClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * repository interface for student class.
 */
public interface InstituteClassRepository extends JpaRepository<InstituteClass, Long> {
    Set<InstituteClass> getAllByInstituteInstituteId(long instituteId);
}

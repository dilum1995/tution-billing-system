package com.onlineschoolbillingsystem.repository;

import com.onlineschoolbillingsystem.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository interface for institute.
 */
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Institute findInstituteByEmail(String email);
}

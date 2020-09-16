package com.onlineschoolbillingsystem.repository;

import com.onlineschoolbillingsystem.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Institute findInstituteByEmail(String email);
}

package com.buana.backend.repository;

import com.buana.backend.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByEmployee_EmployeeId(Long employeeId);
}

package com.buana.backend.repository;

import com.buana.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.manager.id = :managerId")
    List<Employee> findEmployeesByEmployeeManagerId(Long managerId);
}

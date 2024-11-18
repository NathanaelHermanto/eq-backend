package com.buana.backend.service;

import com.buana.backend.model.Employee;
import com.buana.backend.model.EmployeeView;
import com.buana.backend.repository.EmployeeRepository;
import com.buana.backend.repository.EmployeeViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeViewRepository employeeViewRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeView> getAllEmployeeViews() {
        return employeeViewRepository.findAll();
    }
}

package com.buana.backend.controller;

import com.buana.backend.model.Employee;
import com.buana.backend.model.EmployeeView;
import com.buana.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/view")
    public List<EmployeeView> getEmployeeViews() {
        return employeeService.getAllEmployeeViews();
    }
}

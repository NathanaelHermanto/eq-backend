package com.buana.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Immutable
@Table(name = "employee_view")
public class EmployeeView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String employeeName;
    private String managerName;
    private int pathLevel;
    private String employeeFormat;
    private String path_hierarchy;
}

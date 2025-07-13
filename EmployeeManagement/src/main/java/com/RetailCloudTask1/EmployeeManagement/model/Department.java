package com.RetailCloudTask1.EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "head_id")
    @JsonIgnoreProperties({"department", "reportingManager", "hibernateLazyInitializer", "handler"})
    private Employee departmentHead;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"department", "reportingManager", "hibernateLazyInitializer", "handler"})
    private List<Employee> employees;
}

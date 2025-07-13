package com.RetailCloudTask1.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
    private LocalDate creationDate;
}

package com.RetailCloudTask1.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
public class DepartmentWithEmployeeDto {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private List<EmployeeLookupDto> employees;
}

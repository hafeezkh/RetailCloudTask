package com.RetailCloudTask1.EmployeeManagement.dto;

import com.RetailCloudTask1.EmployeeManagement.model.Employee;
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
    private Employee departmentHead;
    private List<EmployeeLookupDto> employees;
}

package com.RetailCloudTask1.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Double salary;
    private String address;
    private String role;
    private LocalDate joiningDate;
    private Double yearlyBonusPercentage;

    private Long departmentId;
    private String departmentName;

    private Long managerId;
    private String managerName;
}

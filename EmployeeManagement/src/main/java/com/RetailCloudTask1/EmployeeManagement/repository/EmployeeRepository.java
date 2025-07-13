package com.RetailCloudTask1.EmployeeManagement.repository;

import com.RetailCloudTask1.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

package com.RetailCloudTask1.EmployeeManagement.repository;

import com.RetailCloudTask1.EmployeeManagement.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findAll(Pageable pageable);
}

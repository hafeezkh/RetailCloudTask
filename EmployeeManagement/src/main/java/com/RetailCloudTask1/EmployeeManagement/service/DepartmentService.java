package com.RetailCloudTask1.EmployeeManagement.service;

import com.RetailCloudTask1.EmployeeManagement.dto.DepartmentDto;
import com.RetailCloudTask1.EmployeeManagement.dto.DepartmentWithEmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeLookupDto;
import com.RetailCloudTask1.EmployeeManagement.model.Department;
import com.RetailCloudTask1.EmployeeManagement.model.Employee;
import com.RetailCloudTask1.EmployeeManagement.repository.DepartmentRepository;
import com.RetailCloudTask1.EmployeeManagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public Department addDepartment(Department department) {
        if (department.getDepartmentHead() != null && department.getDepartmentHead().getId() != null) {
            Employee head = employeeRepository.findById(department.getDepartmentHead().getId())
                    .orElseThrow(() -> new RuntimeException("Department head not found with id: " + department.getDepartmentHead().getId()));
            department.setDepartmentHead(head);
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existing.setName(updatedDepartment.getName());
        existing.setCreationDate(updatedDepartment.getCreationDate());

        if (updatedDepartment.getDepartmentHead() != null && updatedDepartment.getDepartmentHead().getId() != null) {
            Employee head = employeeRepository.findById(updatedDepartment.getDepartmentHead().getId())
                    .orElseThrow(() -> new RuntimeException("Department head not found with id: " + updatedDepartment.getDepartmentHead().getId()));
            existing.setDepartmentHead(head);
        } else {
            existing.setDepartmentHead(null);
        }

        return departmentRepository.save(existing);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with assigned employees.");
        }

        departmentRepository.deleteById(id);
    }

    // Pagination support with employees excluded
    public Page<Department> getAllDepartmentsWithoutEmployees(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    // Pagination support with employees included (mapping done later)
    public Page<Department> getAllDepartmentsWithEmployees(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    // Single department WITHOUT employees
    public DepartmentDto getDepartmentWithoutEmployees(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return new DepartmentDto(dept.getId(), dept.getName(), dept.getCreationDate());
    }

    // Single department WITH employees
    public DepartmentWithEmployeeDto getDepartmentWithEmployees(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDepartmentWithEmployeeDto(dept);
    }

    // Map Department entity to DTO with employee summaries
    public DepartmentWithEmployeeDto mapToDepartmentWithEmployeeDto(Department dept) {
        List<EmployeeLookupDto> employeeDtos = dept.getEmployees()
                .stream()
                .map(emp -> new EmployeeLookupDto(emp.getId(), emp.getName()))
                .collect(Collectors.toList());

        return new DepartmentWithEmployeeDto(
                dept.getId(),
                dept.getName(),
                dept.getCreationDate(),
                dept.getDepartmentHead(),
                employeeDtos
        );
    }
}

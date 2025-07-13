package com.RetailCloudTask1.EmployeeManagement.service;

import com.RetailCloudTask1.EmployeeManagement.dto.DepartmentWithEmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeLookupDto;
import com.RetailCloudTask1.EmployeeManagement.model.Department;
import com.RetailCloudTask1.EmployeeManagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existing.setName(updatedDepartment.getName());
        existing.setCreationDate(updatedDepartment.getCreationDate());
        existing.setDepartmentHead(updatedDepartment.getDepartmentHead());

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

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public Object getDepartmentDetails(Long id, String expand) {
        Department department = getDepartmentById(id);

        if ("employee".equalsIgnoreCase(expand)) {
            List<EmployeeLookupDto> employeeDtos = department.getEmployees()
                    .stream()
                    .map(emp -> new EmployeeLookupDto(emp.getId(), emp.getName()))
                    .collect(Collectors.toList());

            return new DepartmentWithEmployeeDto(
                    department.getId(),
                    department.getName(),
                    department.getCreationDate(),
                    employeeDtos
            );
        }

        return department;
    }
}

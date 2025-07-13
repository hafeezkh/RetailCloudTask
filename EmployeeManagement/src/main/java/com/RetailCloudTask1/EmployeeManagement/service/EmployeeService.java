package com.RetailCloudTask1.EmployeeManagement.service;

import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeLookupDto;
import com.RetailCloudTask1.EmployeeManagement.model.Department;
import com.RetailCloudTask1.EmployeeManagement.model.Employee;
import com.RetailCloudTask1.EmployeeManagement.repository.DepartmentRepository;
import com.RetailCloudTask1.EmployeeManagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> saveAllEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existing.setName(updatedEmployee.getName());
        existing.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setAddress(updatedEmployee.getAddress());
        existing.setRole(updatedEmployee.getRole());
        existing.setJoiningDate(updatedEmployee.getJoiningDate());
        existing.setYearlyBonusPercentage(updatedEmployee.getYearlyBonusPercentage());

        if (updatedEmployee.getDepartment() != null && updatedEmployee.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedEmployee.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existing.setDepartment(dept);
        } else {
            existing.setDepartment(null);
        }

        if (updatedEmployee.getReportingManager() != null && updatedEmployee.getReportingManager().getId() != null) {
            Employee manager = employeeRepository.findById(updatedEmployee.getReportingManager().getId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            existing.setReportingManager(manager);
        } else {
            existing.setReportingManager(null);
        }

        return employeeRepository.save(existing);
    }

    @Transactional
    public List<Employee> updateEmployees(List<Employee> updatedEmployees) {
        return updatedEmployees.stream().map(emp -> {
            Employee existing = employeeRepository.findById(emp.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + emp.getId()));

            existing.setName(emp.getName());
            existing.setDateOfBirth(emp.getDateOfBirth());
            existing.setSalary(emp.getSalary());
            existing.setAddress(emp.getAddress());
            existing.setRole(emp.getRole());
            existing.setJoiningDate(emp.getJoiningDate());
            existing.setYearlyBonusPercentage(emp.getYearlyBonusPercentage());

            if (emp.getDepartment() != null && emp.getDepartment().getId() != null) {
                Department dept = departmentRepository.findById(emp.getDepartment().getId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));
                existing.setDepartment(dept);
            } else {
                existing.setDepartment(null);
            }

            if (emp.getReportingManager() != null && emp.getReportingManager().getId() != null) {
                Employee manager = employeeRepository.findById(emp.getReportingManager().getId())
                        .orElseThrow(() -> new RuntimeException("Manager not found"));
                existing.setReportingManager(manager);
            } else {
                existing.setReportingManager(null);
            }

            return existing;
        }).map(employeeRepository::save).toList();
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        departmentRepository.findAll().forEach(dept -> {
            if (dept.getDepartmentHead() != null && dept.getDepartmentHead().getId().equals(id)) {
                dept.setDepartmentHead(null);
                departmentRepository.save(dept);
            }
        });

        employeeRepository.deleteById(id);
    }

    public EmployeeDto getEmployeeDtoById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return convertToEmployeeDto(emp);
    }

    public List<EmployeeLookupDto> getAllNameAndIds() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> new EmployeeLookupDto(e.getId(), e.getName()))
                .toList();
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public EmployeeDto convertToEmployeeDto(Employee emp) {
        return EmployeeDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .dateOfBirth(emp.getDateOfBirth())
                .salary(emp.getSalary())
                .address(emp.getAddress())
                .role(emp.getRole())
                .joiningDate(emp.getJoiningDate())
                .yearlyBonusPercentage(emp.getYearlyBonusPercentage())
                .departmentId(emp.getDepartment() != null ? emp.getDepartment().getId() : null)
                .departmentName(emp.getDepartment() != null ? emp.getDepartment().getName() : null)
                .managerId(emp.getReportingManager() != null ? emp.getReportingManager().getId() : null)
                .managerName(emp.getReportingManager() != null ? emp.getReportingManager().getName() : null)
                .build();
    }

    @Transactional
    public Employee changeDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(dept);

        if (dept.getDepartmentHead() != null) {
            employee.setReportingManager(dept.getDepartmentHead());
        } else {
            employee.setReportingManager(null);
        }
        return employeeRepository.save(employee);
    }
}

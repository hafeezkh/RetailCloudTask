package com.RetailCloudTask1.EmployeeManagement.controller;

import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeLookupDto;
import com.RetailCloudTask1.EmployeeManagement.model.Employee;
import com.RetailCloudTask1.EmployeeManagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeLookupDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllNameAndIds());
    }

    @PutMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<Employee> changeDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.changeDepartment(employeeId, departmentId));
    }
}

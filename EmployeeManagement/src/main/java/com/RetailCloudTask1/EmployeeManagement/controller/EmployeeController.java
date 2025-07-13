package com.RetailCloudTask1.EmployeeManagement.controller;

import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.dto.EmployeeLookupDto;
import com.RetailCloudTask1.EmployeeManagement.model.Employee;
import com.RetailCloudTask1.EmployeeManagement.service.EmployeeService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> createEmployeeOrList(@RequestBody String rawJson) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class);
            List<Employee> employees = objectMapper.readValue(rawJson, type);
            return ResponseEntity.ok(employeeService.saveAllEmployees(employees));
        } catch (Exception e) {
            try {
                Employee employee = objectMapper.readValue(rawJson, Employee.class);
                return ResponseEntity.ok(employeeService.createEmployee(employee));
            } catch (Exception ex) {
                return ResponseEntity.badRequest().body("Invalid JSON format");
            }
        }
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
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(value = "lookup", required = false) Boolean lookup) {

        Pageable pageable = PageRequest.of(page, 20);

        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);

        if (lookup != null && lookup) {
            List<EmployeeLookupDto> lookups = employeePage.getContent()
                    .stream()
                    .map(emp -> new EmployeeLookupDto(emp.getId(), emp.getName()))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("employees", lookups);
            response.put("currentPage", employeePage.getNumber());
            response.put("totalPages", employeePage.getTotalPages());
            response.put("totalItems", employeePage.getTotalElements());

            return ResponseEntity.ok(response);
        } else {
            List<EmployeeDto> fullList = employeePage.getContent()
                    .stream()
                    .map(employeeService::convertToEmployeeDto)
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("employees", fullList);
            response.put("currentPage", employeePage.getNumber());
            response.put("totalPages", employeePage.getTotalPages());
            response.put("totalItems", employeePage.getTotalElements());

            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<Employee> changeDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.changeDepartment(employeeId, departmentId));
    }
}


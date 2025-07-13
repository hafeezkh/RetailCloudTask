package com.RetailCloudTask1.EmployeeManagement.controller;

import com.RetailCloudTask1.EmployeeManagement.dto.DepartmentDto;
import com.RetailCloudTask1.EmployeeManagement.dto.DepartmentWithEmployeeDto;
import com.RetailCloudTask1.EmployeeManagement.model.Department;
import com.RetailCloudTask1.EmployeeManagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAllDepartments(
            @RequestParam(value = "expand", required = false) String expand,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 20);

        if ("employee".equalsIgnoreCase(expand)) {
            Page<Department> departmentPage = departmentService.getAllDepartmentsWithEmployees(pageable);

            List<DepartmentWithEmployeeDto> departments = departmentPage.getContent()
                    .stream()
                    .map(departmentService::mapToDepartmentWithEmployeeDto)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("departments", departments);
            response.put("currentPage", departmentPage.getNumber());
            response.put("totalPages", departmentPage.getTotalPages());
            response.put("totalItems", departmentPage.getTotalElements());

            return ResponseEntity.ok(response);
        } else {
            Page<Department> departmentPage = departmentService.getAllDepartmentsWithoutEmployees(pageable);

            List<DepartmentDto> departments = departmentPage.getContent()
                    .stream()
                    .map(dept -> new DepartmentDto(dept.getId(), dept.getName(), dept.getCreationDate()))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("departments", departments);
            response.put("currentPage", departmentPage.getNumber());
            response.put("totalPages", departmentPage.getTotalPages());
            response.put("totalItems", departmentPage.getTotalElements());

            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id,
                                               @RequestParam(value = "expand", required = false) String expand) {
        if ("employee".equalsIgnoreCase(expand)) {
            DepartmentWithEmployeeDto dto = departmentService.getDepartmentWithEmployees(id);
            return ResponseEntity.ok(dto);
        } else {
            DepartmentDto dto = departmentService.getDepartmentWithoutEmployees(id);
            return ResponseEntity.ok(dto);
        }
    }


    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.addDepartment(department);
        return ResponseEntity.ok(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
        Department updated = departmentService.updateDepartment(id, updatedDepartment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}

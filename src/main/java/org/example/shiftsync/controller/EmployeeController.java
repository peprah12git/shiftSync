package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.example.shiftsync.service.EmployeeService;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * POST /api/employees
     * HR_ADMIN only — creates an employee profile for an already-registered user.
     */
    @PreAuthorize("hasRole('HR_ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/employees/{id}
     * HR_ADMIN or MANAGER — look up an employee profile by Employee ID.
     * Note: this is the Employee PK, not the User ID.
     */
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    /**
     * GET /api/employees
     * HR_ADMIN or MANAGER — list all employee profiles.
     */
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(defaultValue = 0) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "Department") String
    ) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}


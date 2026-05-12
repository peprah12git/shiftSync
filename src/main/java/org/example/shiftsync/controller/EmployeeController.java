package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.EmployeeFilterDTO;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.example.shiftsync.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    /**
     * GET /api/employees/{id}
     * HR_ADMIN or MANAGER — look up an employee profile by Employee ID.
     */
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    /**
     * GET /api/employees
     * HR_ADMIN or MANAGER — filterable, paginated list of employees.
     *   GET /api/employees?name=john&departmentId=2&employmentType=FULL_TIME&page=0&size=10&sort=hireDate,desc
     */
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @ModelAttribute EmployeeFilterDTO filter,
            @PageableDefault(size = 10, sort = "hireDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(filter, pageable));
    }
}

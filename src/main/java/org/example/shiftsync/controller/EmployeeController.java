package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.EmployeeFilterDTO;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.example.shiftsync.dto.EmployeeUpdateDTO;
import org.example.shiftsync.service.EmployeeServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    /**
     * POST /api/employees
     * HR_ADMIN only — creates an employee profile for an already-registered user.
     */
    @PreAuthorize("hasRole('HR_ADMIN')")
    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    /**
     * GET /api/employees/me — any authenticated employee sees their own profile.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<EmployeeResponseDTO> getMe() {
        return ResponseEntity.ok(employeeService.getMe());
    }

    /**
     * PATCH /api/employees/me — employee updates their own phone / skill tags only.
     * Sending employmentType, departmentId, or locationId returns 403.
     */
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me")
    public ResponseEntity<EmployeeResponseDTO> updateMe(
            @Valid @RequestBody EmployeeUpdateDTO dto) {
        return ResponseEntity.ok(employeeService.updateMe(dto));
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
    @GetMapping("/getALlEmployees")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @ModelAttribute EmployeeFilterDTO filter,
            @PageableDefault(size = 10, sort = "hireDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(filter, pageable));
    }

    @PreAuthorize("hasRole('HR_ADMIN')")
    @PatchMapping("/delete/Employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deactivateEmployee(id);
        return ResponseEntity.noContent().build();
    }
}

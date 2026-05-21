package org.example.shiftsync.controller;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.DepartmentDTO;
import org.example.shiftsync.service.DepartmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations/{locationId}/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentDTO departmentDTO,
            @PathVariable Long locationId) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentDTO, locationId), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsForLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(departmentService.findAllDepartmentsInLocation(locationId));
    }
}

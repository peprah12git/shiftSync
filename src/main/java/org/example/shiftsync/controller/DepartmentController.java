package org.example.shiftsync.controller;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.DepartmentDTO;
import org.example.shiftsync.service.DepartmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @PostMapping("/locations/{locationId}")
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentDTO departmentDTO,
            @PathVariable Long locationId) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentDTO, locationId), HttpStatus.CREATED);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentforLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(departmentService.findAllDepartmentsInLocation(locationId));
    }
}

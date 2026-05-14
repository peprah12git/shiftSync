package org.example.shiftsync.controller;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.service.AuthServiceImpl;
import org.example.shiftsync.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AuthServiceImpl authService;

    @PreAuthorize("hasRole('HR_ADMIN')")
    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        userService.updateRole(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }
}

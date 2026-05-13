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

    @PreAuthorize("hasRole('HR_ADMIN')")
    @PostMapping("/managers/{userId}/locations/{locationId}")
    public ResponseEntity<String> assignManagerToLocation(
            @PathVariable Long userId,
            @PathVariable Long locationId) {

        userService.assignManagerToLocation(userId, locationId);
        return ResponseEntity.ok("Manager assigned to location successfully");
    }

    @PreAuthorize("hasRole('HR_ADMIN')")
    @DeleteMapping("/managers/{userId}/locations/{locationId}")
    public ResponseEntity<String> removeManagerFromLocation(
            @PathVariable Long userId,
            @PathVariable Long locationId) {

        userService.removeManagerFromLocation(userId, locationId);
        return ResponseEntity.ok("Manager removed from location successfully");
    }
}

package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.LocationDTO;
import org.example.shiftsync.service.LocationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationServiceImpl locationService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.createLocation(dto));
    }

    @GetMapping("/all/locations")
    @PreAuthorize("hasAnyRole('HR_ADMIN')")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @PutMapping("/location/{id}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDTO dto) {
        return ResponseEntity.ok(locationService.updateLocation(id, dto));
    }

    @PatchMapping("/delete-location/{id}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<List<LocationDTO>> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
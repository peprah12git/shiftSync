package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.Location;
import org.example.shiftsync.Entity.ManagerLocation;
import org.example.shiftsync.Entity.ManagerLocationId;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.exception.ResourceNotFoundException;
import org.example.shiftsync.repository.LocationRepository;
import org.example.shiftsync.repository.ManagerLocationRepository;
import org.example.shiftsync.repository.UserRepository;
import org.example.shiftsync.service.serviceInterface.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ManagerLocationRepository managerLocationRepository;
    private final LocationRepository locationRepository;

    public void updateRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //  extra safety (optional but good)
        if (role == Role.HR_ADMIN) {
            throw new IllegalStateException("Cannot assign HR_ADMIN role directly");
        }

        if (user.getRole() == Role.MANAGER && role != Role.MANAGER) {
            managerLocationRepository.deleteAll(managerLocationRepository.findByManagerId(userId));
        }

        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void assignManagerToLocation(Long userId, Long locationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (user.getRole() != Role.MANAGER) {
            throw new IllegalStateException(
                    "User is not a MANAGER. Promote the user first via PUT /api/admin/users/{id}/role?role=MANAGER");
        }

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found: " + locationId));

        if (managerLocationRepository.existsByManagerIdAndLocationId(userId, locationId)) {
            throw new IllegalStateException("Manager is already assigned to this location.");
        }

        ManagerLocation ml = ManagerLocation.builder()
                .manager(user)
                .location(location)
                .build();

        managerLocationRepository.save(ml);
    }

    @Transactional
    public void removeManagerFromLocation(Long userId, Long locationId) {
        ManagerLocationId id = new ManagerLocationId(userId, locationId);
        if (!managerLocationRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "No assignment found for manager " + userId + " at location " + locationId);
        }
        managerLocationRepository.deleteById(id);
    }
}

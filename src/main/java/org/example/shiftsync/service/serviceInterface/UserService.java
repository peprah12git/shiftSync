package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.LocationResponseDTO;
import org.example.shiftsync.enums.Role;

import java.util.List;

public interface UserService {
    void updateRole(Long userId, Role role);
    void assignManagerToLocation(Long userId, Long locationId);
    void removeManagerFromLocation(Long userId, Long locationId);
    List<LocationResponseDTO> getMyLocations();
}
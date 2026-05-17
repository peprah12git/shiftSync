package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.enums.Role;

public interface UserService {
    void updateRole(Long userId, Role role);
    void assignManagerToLocation(Long userId, Long locationId);
    void removeManagerFromLocation(Long userId, Long locationId);
}
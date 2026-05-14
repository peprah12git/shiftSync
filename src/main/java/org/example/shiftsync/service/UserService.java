package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void updateRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  extra safety (optional but good)
        if (role == Role.HR_ADMIN) {
            throw new RuntimeException("Cannot assign HR_ADMIN role directly");
        }

        user.setRole(role);
        userRepository.save(user);
    }
}

package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.example.shiftsync.exception.DuplicateEmailException;
import org.example.shiftsync.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registration(RegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail()) )
        {throw new DuplicateEmailException("Email already exists: +" + registrationDTO.getEmail());
        }
        User user = User.builder()
                .fullName(registrationDTO.getFullName())
                .email(registrationDTO.getEmail())
                .passwordHash(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(registrationDTO.getRole())
                .build();
       User saved = userRepository.save(user);
        return new  UserResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getRole()
        );





    }

}

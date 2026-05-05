package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.dto.LoginDTO;
import org.example.shiftsync.dto.LoginResponseDTO;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.exception.DuplicateEmailException;
import org.example.shiftsync.repository.UserRepository;
import org.example.shiftsync.security.JwtTokenService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final JwtTokenService jwtTokenService;
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
                .role(Role.EMPLOYEE)
                .build();
       User saved = userRepository.save(user);
        return new  UserResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getRole()
        );

    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found") {
                });
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtTokenService.generateToken(user);
        return new LoginResponseDTO(user.getFullName(),
                user.getEmail(), token, user.getRole());


    }

}

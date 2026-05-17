package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.RefreshToken;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.dto.LoginDTO;
import org.example.shiftsync.dto.LoginResponseDTO;
import org.example.shiftsync.dto.RefreshTokenResponseDTO;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.exception.DuplicateEmailException;
import org.example.shiftsync.exception.EmailNotFoundException;
import org.example.shiftsync.exception.InvalidTokenException;
import org.example.shiftsync.repository.RefreshTokenRepository;
import org.example.shiftsync.repository.UserRepository;
import org.example.shiftsync.security.JwtTokenService;
import org.example.shiftsync.service.serviceInterface.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    public UserResponse registration(RegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + registrationDTO.getEmail());
        }
        User user = User.builder()
                .fullName(registrationDTO.getFullName())
                .email(registrationDTO.getEmail())
                .passwordHash(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(Role.EMPLOYEE)
                .build();
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getRole());
    }

    @Transactional
    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("No account found for email: " + loginDTO.getEmail()));

        if (!user.isActive()) {
            throw new DisabledException("Account is deactivated");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            throw new InvalidTokenException("Invalid credentials");
        }

        // Invalidate any previous refresh tokens for this user
        refreshTokenRepository.deleteByUser_Id(user.getId());

        String accessToken = jwtTokenService.generateToken(user);
        String rawRefreshToken = issueRefreshToken(user);

        return new LoginResponseDTO(user.getId(), user.getFullName(), user.getEmail(), accessToken, rawRefreshToken, user.getRole());
    }

    @Transactional
    public RefreshTokenResponseDTO refreshToken(String rawToken) {
        RefreshToken stored = refreshTokenRepository.findByToken(rawToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (stored.isRevoked()) {
            throw new InvalidTokenException("Refresh token has been revoked");
        }
        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Refresh token has expired");
        }

        User user = stored.getUser();
        if (!user.isActive()) {
            throw new DisabledException("Account is deactivated");
        }

        // Rotate: revoke the consumed token, issue a fresh one
        stored.setRevoked(true);
        refreshTokenRepository.save(stored);

        String newAccessToken = jwtTokenService.generateToken(user);
        String newRawRefreshToken = issueRefreshToken(user);

        return new RefreshTokenResponseDTO(user.getEmail(), newAccessToken, newRawRefreshToken, user.getRole());
    }

    private String issueRefreshToken(User user) {
        String raw = UUID.randomUUID().toString();
        refreshTokenRepository.save(RefreshToken.builder()
                .token(raw)
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000))
                .build());
        return raw;
    }
}
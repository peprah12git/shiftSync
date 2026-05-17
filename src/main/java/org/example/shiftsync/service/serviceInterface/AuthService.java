package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.LoginDTO;
import org.example.shiftsync.dto.LoginResponseDTO;
import org.example.shiftsync.dto.RefreshTokenResponseDTO;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;

public interface AuthService {
    UserResponse registration(RegistrationDTO registrationDTO);
    LoginResponseDTO login(LoginDTO loginDTO);
    RefreshTokenResponseDTO refreshToken(String rawToken);
}

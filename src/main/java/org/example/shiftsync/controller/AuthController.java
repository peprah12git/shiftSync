package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.*;
import org.example.shiftsync.service.AuthServiceImpl;
import org.example.shiftsync.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
         UserResponse userResponse = authService.registration(registrationDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        RefreshTokenResponseDTO response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

}

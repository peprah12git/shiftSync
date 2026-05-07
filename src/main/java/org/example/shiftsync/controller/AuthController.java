package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.LoginDTO;
import org.example.shiftsync.dto.LoginResponseDTO;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.service.AuthServiceImpl;
import org.example.shiftsync.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
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
    public ResponseEntity<LoginResponseDTO> refresh(@RequestHeader("Authorization") String authorizationHeader) {
        LoginResponseDTO response = authService.refreshToken(authorizationHeader);
        return ResponseEntity.ok(response);
    }

}

package org.example.shiftsync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.example.shiftsync.service.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
         UserResponse userResponse = authService.registration(registrationDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}

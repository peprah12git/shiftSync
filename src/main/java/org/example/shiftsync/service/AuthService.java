package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;


}

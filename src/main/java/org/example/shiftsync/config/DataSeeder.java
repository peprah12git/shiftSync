package org.example.shiftsync.config;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.enums.Role;
import org.example.shiftsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final PasswordEncoder passwordEncoder;
    @Value("${admin_email}")
    private String adminEmail;

    @Value("${admin_password}")
    private String adminPassword;
    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository) {
        return args -> {


            boolean adminExists = userRepository.findByEmail(adminEmail).isPresent();

            if (!adminExists) {

                User admin = User.builder()
                        .fullName("HR_ADMIN")
                        .email(adminEmail)
                        .passwordHash(passwordEncoder.encode(adminPassword))
                        .role(Role.HR_ADMIN)
                        .isActive(true)
                        .build();

                userRepository.save(admin);

                System.out.println(" Admin user created successfully");
            } else {
                System.out.println("ℹ Admin already exists");
            }
        };
    }
}
package org.example.shiftsync.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationDTO {
    private String firstName;
    @NotBlank(message = "email required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "email must include special character")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min =0, max =64, message = "Password must be between 8 and 64 character")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
    message =" Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String passwordHash;
}

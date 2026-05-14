package org.example.shiftsync.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class employeeRegistrationDTO {
    @NotBlank
    private String fullName;
    @NotBlank(message = "email required")
    private String email;

    private String department;

    private String location;

    private String employementType;
}

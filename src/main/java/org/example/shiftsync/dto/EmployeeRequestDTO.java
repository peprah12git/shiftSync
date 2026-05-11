package org.example.shiftsync.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shiftsync.enums.EmploymentType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9\\s\\-]{7,15}$", message = "Phone number is invalid")
    private String phone;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    private List<String> skillTags;

    @NotNull(message = "Contracted weekly hours is required")
    @DecimalMin(value = "1.0", message = "Contracted weekly hours must be at least 1")
    @DecimalMax(value = "168.0", message = "Contracted weekly hours cannot exceed 168")
    private Double contractedWeeklyHours;
}

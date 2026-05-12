package org.example.shiftsync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shiftsync.enums.EmploymentType;
import org.example.shiftsync.enums.Role;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private String employeeId;          // derived: "EMP-0001"
    private Long userId;                // the linked User's PK
    private String fullName;
    private String email;
    private String phone;
    private EmploymentType employmentType;
    private String department;
    private String location;
    private List<String> skillTags;
    private Double contractedWeeklyHours;
    private LocalDate hireDate;
    private Role role;
}

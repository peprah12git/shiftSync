package org.example.shiftsync.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeUpdateDTO {

    @Pattern(regexp = "^\\+?[0-9\\s\\-]{7,15}$", message = "Phone number is invalid")
    private String phone;

    private List<String> skillTags;

    // Guard fields — if the caller sends these we return 403
    private Object employmentType;
    private Object departmentId;
    private Object locationId;
}


package org.example.shiftsync.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shiftsync.enums.EmploymentType;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeFilterDTO {
    private String name;
    private Long departmentId;
    private Long locationId;
    private EmploymentType employmentType;
    private Boolean active = true;
}

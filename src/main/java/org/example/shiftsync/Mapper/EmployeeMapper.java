package org.example.shiftsync.Mapper;

import org.example.shiftsync.Entity.Employee;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponseDTO toDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        // Derive a human-readable employee ID from the auto-generated PK
        dto.setEmployeeId(String.format("EMP-%04d", employee.getId()));
        dto.setUserId(employee.getUser().getId());

        // Fields from the linked User entity
        dto.setFullName(employee.getUser().getFullName());
        dto.setEmail(employee.getUser().getEmail());
        dto.setRole(employee.getUser().getRole());

        // Fields on Employee itself
        dto.setPhone(employee.getPhone());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setLocation(employee.getPrimaryLocation() != null ? employee.getPrimaryLocation().getName() : null);
        dto.setSkillTags(employee.getSkills());
        dto.setContractedWeeklyHours(
                employee.getContractedWeeklyHours() != null
                        ? employee.getContractedWeeklyHours().doubleValue()
                        : null
        );
        dto.setHireDate(employee.getHireDate());

        return dto;
    }
}

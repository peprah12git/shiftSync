package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.EmployeeFilterDTO;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.example.shiftsync.dto.EmployeeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    EmployeeResponseDTO getEmployee(Long id);
    Page<EmployeeResponseDTO> getAllEmployees(EmployeeFilterDTO filter, Pageable pageable);
    EmployeeResponseDTO getMyProfile();
    EmployeeResponseDTO updateMe(EmployeeUpdateDTO dto);
    EmployeeResponseDTO viewEmployeeDetails(Long id);
    void deactivateEmployee(Long id);
}
package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.repository.EmployeeRepository;

@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeRequestDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
       if (employeeRepository.findByUserEmail(employeeRequestDTO.getEmail())){
            throw new RuntimeException("Employee with email " + employeeRequestDTO.getEmail() + " already exists");
        };
    }
}

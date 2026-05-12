package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.Department;
import org.example.shiftsync.Entity.Employee;
import org.example.shiftsync.Entity.Location;
import org.example.shiftsync.Entity.User;
import org.example.shiftsync.Mapper.EmployeeMapper;
import org.example.shiftsync.dto.EmployeeRequestDTO;
import org.example.shiftsync.dto.EmployeeResponseDTO;
import org.example.shiftsync.exception.DuplicateEmailException;
import org.example.shiftsync.exception.ResourceNotFoundException;
import org.example.shiftsync.repository.DepartmentRepository;
import org.example.shiftsync.repository.EmployeeRepository;
import org.example.shiftsync.repository.LocationRepository;
import org.example.shiftsync.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * HR_ADMIN creates an employee profile for an already-registered User.
     * The User must have registered via POST /api/auth/register first.
     */
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        // 1. Resolve the existing User — must already be registered
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No registered user found with ID: " + dto.getUserId()
                        + ". The employee must register first via /api/auth/register."));

        // 2. Guard: one Employee profile per User
        if (employeeRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateEmailException(
                    "An employee profile already exists for user: " + user.getEmail());
        }

        // 3. Resolve Department and Location
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found: " + dto.getDepartmentId()));

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location not found: " + dto.getLocationId()));

        // 4. Build and save Employee profile only
        Employee employee = Employee.builder()
                .user(user)
                .phone(dto.getPhone())
                .employmentType(dto.getEmploymentType())
                .department(department)
                .primaryLocation(location)
                .contractedWeeklyHours(BigDecimal.valueOf(dto.getContractedWeeklyHours()))
                .hireDate(dto.getHireDate())
                .skills(dto.getSkillTags() != null ? dto.getSkillTags() : List.of())
                .build();

        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }

    /**
     * Retrieve a single employee by their Employee PK.
     */
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return employeeMapper.toDTO(employee);
    }

    /**
     * List all employees.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toDTO);  // Page has its own .map(), use that
    }
}

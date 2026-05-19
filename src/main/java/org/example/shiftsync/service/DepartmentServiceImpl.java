package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.Department;
import org.example.shiftsync.Entity.Location;
import org.example.shiftsync.dto.DepartmentDTO;
import org.example.shiftsync.exception.ResourceNotFoundException;
import org.example.shiftsync.repository.DepartmentRepository;
import org.example.shiftsync.repository.LocationRepository;
import org.example.shiftsync.service.serviceInterface.DepartmentInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentInterface {

    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO, Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + locationId));

        if (departmentRepository.existsByNameAndLocation(departmentDTO.getName(), location)) {
            throw new IllegalArgumentException(
                    "Department '" + departmentDTO.getName() + "' already exists in this location");
        }

        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setLocation(location);
        departmentRepository.save(department);
        return departmentDTO;
    }

    @Override
    public List<DepartmentDTO> findAllDepartmentsInLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + locationId));

        return departmentRepository.findAllByLocation(location).stream()
                .map(dept -> new DepartmentDTO(dept.getName()))
                .toList();
    }
}


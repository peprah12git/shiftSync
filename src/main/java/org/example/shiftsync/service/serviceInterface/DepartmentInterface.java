package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentInterface {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO, Long locationId);
    List<DepartmentDTO> findAllDepartmentsInLocation(Long locationId);
}

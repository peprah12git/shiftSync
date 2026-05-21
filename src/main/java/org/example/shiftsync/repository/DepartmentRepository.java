package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.Department;
import org.example.shiftsync.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByNameAndLocation(String name, Location location);

    List<Department> findAllByLocation(Location location);
}


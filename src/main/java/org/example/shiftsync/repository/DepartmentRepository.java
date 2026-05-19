package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.Department;
import org.example.shiftsync.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndLocation(String name, Location location);

    List<Department> findAllByLocation(Location location);

    Optional<Department> findById(Long id);
}


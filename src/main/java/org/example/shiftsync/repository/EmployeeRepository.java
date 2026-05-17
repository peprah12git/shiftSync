package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    boolean existsByUserId(Long userId);

    boolean existsByUserEmail(String email);

    Optional<Employee> findByUserEmail(String email);
}

package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean findByUserEmail(String email);
}

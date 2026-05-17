package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.ManagerLocation;
import org.example.shiftsync.Entity.ManagerLocationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerLocationRepository extends JpaRepository<ManagerLocation, ManagerLocationId> {
    List<ManagerLocation> findByManagerId(Long managerId);
    boolean existsByManagerIdAndLocationId(Long managerId, Long locationId);
}


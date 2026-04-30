package org.example.shiftsync.repository;

import org.example.shiftsync.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);

    boolean existsByEmail(String email);
}

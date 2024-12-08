package com.example.labuser2.repository;

import com.example.labuser2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByRole(String role);
    Optional<User> findByResetToken(String resetToken);
    Optional<User> findByEmail(String email);
}

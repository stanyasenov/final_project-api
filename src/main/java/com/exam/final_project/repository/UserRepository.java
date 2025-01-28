package com.exam.final_project.repository;

import com.exam.final_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findByUsernameContainingIgnoreCase(String query);
}
package com.example.CCINETApplication.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CCINETApplication.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
}
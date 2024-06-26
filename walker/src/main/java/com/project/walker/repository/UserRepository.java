package com.project.walker.repository;

import com.project.core.domain.user.Role;
import com.project.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndRole(final String email, final Role role);
    Optional<User> findByEmail(final String email);
}

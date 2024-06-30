package com.project.walker.repository;

import com.project.core.domain.user.customer.CustomerDog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDogRepository extends JpaRepository<CustomerDog, Long> {
    Optional<CustomerDog> findByUserId(final Long id);
}

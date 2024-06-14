package com.project.customer.user.repository;

import com.project.core.domain.user.customer.CustomerDog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDogRepository extends JpaRepository<CustomerDog, Long> {
}

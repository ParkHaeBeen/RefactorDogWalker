package com.project.customer.repository;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkerServiceRouteRepository extends JpaRepository<WalkerServiceRoute, Long> {
    Optional<WalkerServiceRoute> findByReserve(final WalkerReserve reserve);
}

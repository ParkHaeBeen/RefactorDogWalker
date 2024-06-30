package com.project.walker.repository;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface WalkerServiceRouteRepository extends JpaRepository<WalkerServiceRoute, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO walker_service_route (walker_reserve_id, route, created_at) VALUES (:reserveId, ST_GeomFromText(:lineString, 4326), now())", nativeQuery = true)
    int save(@Param("reserveId") final Long reserveId, @Param("lineString") final String lineString);

    Optional<WalkerServiceRoute> findByReserve(final WalkerReserve reserve);
}

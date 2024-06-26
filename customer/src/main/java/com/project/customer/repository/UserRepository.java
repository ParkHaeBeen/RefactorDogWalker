package com.project.customer.repository;

import com.project.core.domain.user.Role;
import com.project.core.domain.user.User;
import com.project.customer.repository.queryDsl.UserCustomRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(final String email);

    Optional<User> findByEmailAndRole(final String email, final Role role);

    @Query("SELECT user from User user " +
            "where st_contains(st_buffer(:center, :radius), user.location) " +
            "and user.role = 'WALKER'")
    List<User> findAllWithCircleArea(
            @Param("center") final Point center,
            @Param("radius") final int radius,
            Pageable pageable
    );

    @Query("SELECT user from User user " +
            "where st_contains(st_buffer(:center, :radius), user.location) and user.name = :name " +
            "and user.role = 'WALKER'")
    List<User> findAllWithCircleAreaAndName(
            @Param("center") final Point center,
            @Param("radius") final int radius,
            @Param("name") final String name,
            Pageable pageable
    );
}

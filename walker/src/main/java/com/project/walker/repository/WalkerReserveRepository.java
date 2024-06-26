package com.project.walker.repository;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkerReserveRepository extends JpaRepository<WalkerReserve, Long> {
    Page<WalkerReserve> findByWalkerAndStatus(final User user, final WalkerServiceStatus status, final Pageable pageable);
    Optional<WalkerReserve> findByIdAndStatus(final Long id, final WalkerServiceStatus status);
    Optional<WalkerReserve> findByIdAndWalkerAndStatus(final Long id, final User user, final WalkerServiceStatus status);
}

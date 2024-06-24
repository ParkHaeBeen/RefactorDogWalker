package com.project.customer.walkerSearch.repository;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WalkerReserveRepository extends JpaRepository<WalkerReserve, Long> {
    List<WalkerReserve> findByWalkerAndStatusAndDateBetween(final User walker, final WalkerServiceStatus status, final LocalDateTime start, final LocalDateTime end);
    Optional<WalkerReserve> findByWalkerIdAndDate(final Long id, final LocalDateTime date);
}

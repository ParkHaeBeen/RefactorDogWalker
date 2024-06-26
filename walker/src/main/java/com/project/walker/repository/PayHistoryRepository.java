package com.project.walker.repository;

import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long> {
    Optional<PayHistory> findByReserve(final WalkerReserve reserve);

}

package com.project.customer.repository;

import com.project.core.domain.user.walker.WalkerScheduleTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WalkerScheduleTempRepository extends JpaRepository<WalkerScheduleTemp, Long> {
    List<WalkerScheduleTemp> findByWalkerIdAndUnAvailAtGreaterThan(final Long id, final LocalDate today);
}

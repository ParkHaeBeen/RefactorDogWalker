package com.project.customer.walkerSearch.repository;

import com.project.core.domain.user.walker.WalkerSchedulePerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkerSchedulePermRepository extends JpaRepository<WalkerSchedulePerm, Long> {
    List<WalkerSchedulePerm> findByWalkerId(final Long id);
}

package com.project.walker.user.repository;

import com.project.core.domain.user.walker.WalkerSchedulePerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkerSchedulePermRepository extends JpaRepository<WalkerSchedulePerm, Long> {
}

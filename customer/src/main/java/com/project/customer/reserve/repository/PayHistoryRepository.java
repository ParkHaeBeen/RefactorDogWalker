package com.project.customer.reserve.repository;

import com.project.core.domain.reserve.PayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long> {
}

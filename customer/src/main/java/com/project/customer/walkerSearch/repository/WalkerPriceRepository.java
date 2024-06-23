package com.project.customer.walkerSearch.repository;

import com.project.core.domain.user.walker.WalkerPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkerPriceRepository extends JpaRepository<WalkerPrice, Long> {
    List<WalkerPrice> findByWalkerId(final Long id);
}

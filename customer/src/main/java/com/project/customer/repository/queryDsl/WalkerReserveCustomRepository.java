package com.project.customer.repository.queryDsl;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalkerReserveCustomRepository {
    List<WalkerReserve> search(final User user, final WalkerServiceStatus status, final Pageable pageable);
}

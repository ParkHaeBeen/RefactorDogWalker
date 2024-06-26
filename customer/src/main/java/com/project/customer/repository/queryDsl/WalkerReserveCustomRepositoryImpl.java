package com.project.customer.repository.queryDsl;

import com.project.core.common.service.QueryDslUtil;
import com.project.core.domain.reserve.QWalkerReserve;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class WalkerReserveCustomRepositoryImpl implements WalkerReserveCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<WalkerReserve> search(final User user, final WalkerServiceStatus status, final Pageable pageable) {
        final BooleanBuilder builder = new BooleanBuilder();
        builder.and(QWalkerReserve.walkerReserve.customer.eq(user));

        if(status != null) {
            builder.and(QWalkerReserve.walkerReserve.status.eq(status));
        }

        final JPAQuery<WalkerReserve> query = queryFactory
                .selectFrom(QWalkerReserve.walkerReserve)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        QueryDslUtil.orders(pageable, QWalkerReserve.walkerReserve)
                .forEach(query::orderBy);

        return query.fetch();
    }
}

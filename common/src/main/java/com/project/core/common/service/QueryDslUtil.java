package com.project.core.common.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryDslUtil {
    public static List<OrderSpecifier> orders(final Pageable pageable, final Path<?> path) {
        final List<OrderSpecifier> orders = new ArrayList<>();

        if(!pageable.getSort().isEmpty()) {
            pageable.getSort().stream().forEach(sort -> {
                final Order order = sort.isAscending() ? Order.ASC : Order.DESC;

                orders.add(new OrderSpecifier(order, Expressions.path(Object.class, path, sort.getProperty())));
            });
        }

        return orders;
    }
}

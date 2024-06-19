package com.project.core.domain.user.walker;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWalkerPrice is a Querydsl query type for WalkerPrice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerPrice extends EntityPathBase<WalkerPrice> {

    private static final long serialVersionUID = -1237055583L;

    public static final QWalkerPrice walkerPrice = new QWalkerPrice("walkerPrice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> timeUnit = createNumber("timeUnit", Integer.class);

    public final NumberPath<Long> walkerId = createNumber("walkerId", Long.class);

    public QWalkerPrice(String variable) {
        super(WalkerPrice.class, forVariable(variable));
    }

    public QWalkerPrice(Path<? extends WalkerPrice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWalkerPrice(PathMetadata metadata) {
        super(WalkerPrice.class, metadata);
    }

}


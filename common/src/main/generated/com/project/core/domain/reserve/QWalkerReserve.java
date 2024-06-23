package com.project.core.domain.reserve;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkerReserve is a Querydsl query type for WalkerReserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerReserve extends EntityPathBase<WalkerReserve> {

    private static final long serialVersionUID = 1739813463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkerReserve walkerReserve = new QWalkerReserve("walkerReserve");

    public final com.project.core.domain.QBaseEntity _super = new com.project.core.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.project.core.domain.user.QUser customer;

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<WalkerServiceStatus> status = createEnum("status", WalkerServiceStatus.class);

    public final NumberPath<Integer> timeUnit = createNumber("timeUnit", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.project.core.domain.user.QUser walker;

    public QWalkerReserve(String variable) {
        this(WalkerReserve.class, forVariable(variable), INITS);
    }

    public QWalkerReserve(Path<? extends WalkerReserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkerReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkerReserve(PathMetadata metadata, PathInits inits) {
        this(WalkerReserve.class, metadata, inits);
    }

    public QWalkerReserve(Class<? extends WalkerReserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.project.core.domain.user.QUser(forProperty("customer")) : null;
        this.walker = inits.isInitialized("walker") ? new com.project.core.domain.user.QUser(forProperty("walker")) : null;
    }

}


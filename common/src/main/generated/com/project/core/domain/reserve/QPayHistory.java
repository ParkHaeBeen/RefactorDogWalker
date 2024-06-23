package com.project.core.domain.reserve;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayHistory is a Querydsl query type for PayHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayHistory extends EntityPathBase<PayHistory> {

    private static final long serialVersionUID = -1020887621L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayHistory payHistory = new QPayHistory("payHistory");

    public final com.project.core.domain.QBaseEntity _super = new com.project.core.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.project.core.domain.user.QUser customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath method = createString("method");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QWalkerReserve reserve;

    public final EnumPath<PayStatus> status = createEnum("status", PayStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPayHistory(String variable) {
        this(PayHistory.class, forVariable(variable), INITS);
    }

    public QPayHistory(Path<? extends PayHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayHistory(PathMetadata metadata, PathInits inits) {
        this(PayHistory.class, metadata, inits);
    }

    public QPayHistory(Class<? extends PayHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.project.core.domain.user.QUser(forProperty("customer")) : null;
        this.reserve = inits.isInitialized("reserve") ? new QWalkerReserve(forProperty("reserve"), inits.get("reserve")) : null;
    }

}


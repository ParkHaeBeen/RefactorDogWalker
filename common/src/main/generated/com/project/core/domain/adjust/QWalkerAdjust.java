package com.project.core.domain.adjust;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkerAdjust is a Querydsl query type for WalkerAdjust
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerAdjust extends EntityPathBase<WalkerAdjust> {

    private static final long serialVersionUID = -1672042297L;

    public static final QWalkerAdjust walkerAdjust = new QWalkerAdjust("walkerAdjust");

    public final com.project.core.domain.QBaseEntity _super = new com.project.core.domain.QBaseEntity(this);

    public final ListPath<WalkerAdjustDetail, QWalkerAdjustDetail> adjustDetailList = this.<WalkerAdjustDetail, QWalkerAdjustDetail>createList("adjustDetailList", WalkerAdjustDetail.class, QWalkerAdjustDetail.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> end = createDate("end", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final DatePath<java.time.LocalDate> start = createDate("start", java.time.LocalDate.class);

    public final EnumPath<AdjustStatus> status = createEnum("status", AdjustStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QWalkerAdjust(String variable) {
        super(WalkerAdjust.class, forVariable(variable));
    }

    public QWalkerAdjust(Path<? extends WalkerAdjust> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWalkerAdjust(PathMetadata metadata) {
        super(WalkerAdjust.class, metadata);
    }

}


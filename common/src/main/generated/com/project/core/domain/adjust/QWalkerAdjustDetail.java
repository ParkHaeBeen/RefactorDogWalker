package com.project.core.domain.adjust;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkerAdjustDetail is a Querydsl query type for WalkerAdjustDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerAdjustDetail extends EntityPathBase<WalkerAdjustDetail> {

    private static final long serialVersionUID = 864009208L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkerAdjustDetail walkerAdjustDetail = new QWalkerAdjustDetail("walkerAdjustDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.core.domain.reserve.QPayHistory payHistory;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<AdjustDetailStatus> status = createEnum("status", AdjustDetailStatus.class);

    public final QWalkerAdjust walkerAdjust;

    public QWalkerAdjustDetail(String variable) {
        this(WalkerAdjustDetail.class, forVariable(variable), INITS);
    }

    public QWalkerAdjustDetail(Path<? extends WalkerAdjustDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkerAdjustDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkerAdjustDetail(PathMetadata metadata, PathInits inits) {
        this(WalkerAdjustDetail.class, metadata, inits);
    }

    public QWalkerAdjustDetail(Class<? extends WalkerAdjustDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payHistory = inits.isInitialized("payHistory") ? new com.project.core.domain.reserve.QPayHistory(forProperty("payHistory"), inits.get("payHistory")) : null;
        this.walkerAdjust = inits.isInitialized("walkerAdjust") ? new QWalkerAdjust(forProperty("walkerAdjust")) : null;
    }

}


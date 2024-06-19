package com.project.core.domain.user.walker;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWalkerSchedulePerm is a Querydsl query type for WalkerSchedulePerm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerSchedulePerm extends EntityPathBase<WalkerSchedulePerm> {

    private static final long serialVersionUID = 123317455L;

    public static final QWalkerSchedulePerm walkerSchedulePerm = new QWalkerSchedulePerm("walkerSchedulePerm");

    public final StringPath dayOfWeek = createString("dayOfWeek");

    public final NumberPath<Integer> endTime = createNumber("endTime", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> startTime = createNumber("startTime", Integer.class);

    public final NumberPath<Long> walkerId = createNumber("walkerId", Long.class);

    public QWalkerSchedulePerm(String variable) {
        super(WalkerSchedulePerm.class, forVariable(variable));
    }

    public QWalkerSchedulePerm(Path<? extends WalkerSchedulePerm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWalkerSchedulePerm(PathMetadata metadata) {
        super(WalkerSchedulePerm.class, metadata);
    }

}


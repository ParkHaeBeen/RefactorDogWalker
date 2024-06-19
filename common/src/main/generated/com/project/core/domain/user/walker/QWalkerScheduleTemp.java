package com.project.core.domain.user.walker;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWalkerScheduleTemp is a Querydsl query type for WalkerScheduleTemp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerScheduleTemp extends EntityPathBase<WalkerScheduleTemp> {

    private static final long serialVersionUID = 123436467L;

    public static final QWalkerScheduleTemp walkerScheduleTemp = new QWalkerScheduleTemp("walkerScheduleTemp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> unAvailAt = createDate("unAvailAt", java.time.LocalDate.class);

    public final NumberPath<Long> walkerId = createNumber("walkerId", Long.class);

    public QWalkerScheduleTemp(String variable) {
        super(WalkerScheduleTemp.class, forVariable(variable));
    }

    public QWalkerScheduleTemp(Path<? extends WalkerScheduleTemp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWalkerScheduleTemp(PathMetadata metadata) {
        super(WalkerScheduleTemp.class, metadata);
    }

}


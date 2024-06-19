package com.project.core.domain.walkerservice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalkerServiceRoute is a Querydsl query type for WalkerServiceRoute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalkerServiceRoute extends EntityPathBase<WalkerServiceRoute> {

    private static final long serialVersionUID = -1268543434L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalkerServiceRoute walkerServiceRoute = new QWalkerServiceRoute("walkerServiceRoute");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.core.domain.reserve.QWalkerReserve reserve;

    public final ComparablePath<org.locationtech.jts.geom.Geometry> routes = createComparable("routes", org.locationtech.jts.geom.Geometry.class);

    public QWalkerServiceRoute(String variable) {
        this(WalkerServiceRoute.class, forVariable(variable), INITS);
    }

    public QWalkerServiceRoute(Path<? extends WalkerServiceRoute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalkerServiceRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalkerServiceRoute(PathMetadata metadata, PathInits inits) {
        this(WalkerServiceRoute.class, metadata, inits);
    }

    public QWalkerServiceRoute(Class<? extends WalkerServiceRoute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reserve = inits.isInitialized("reserve") ? new com.project.core.domain.reserve.QWalkerReserve(forProperty("reserve"), inits.get("reserve")) : null;
    }

}


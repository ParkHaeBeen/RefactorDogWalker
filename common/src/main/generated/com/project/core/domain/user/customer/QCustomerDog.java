package com.project.core.domain.user.customer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerDog is a Querydsl query type for CustomerDog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerDog extends EntityPathBase<CustomerDog> {

    private static final long serialVersionUID = -1788586092L;

    public static final QCustomerDog customerDog = new QCustomerDog("customerDog");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath name = createString("name");

    public final StringPath type = createString("type");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCustomerDog(String variable) {
        super(CustomerDog.class, forVariable(variable));
    }

    public QCustomerDog(Path<? extends CustomerDog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerDog(PathMetadata metadata) {
        super(CustomerDog.class, metadata);
    }

}


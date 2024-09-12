package com.kosa.chanzipup.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1839383031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.kosa.chanzipup.domain.account.company.QCompany company;

    public final StringPath content = createString("content");

    public final com.kosa.chanzipup.domain.estimate.QEstimate estimate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.chanzipup.domain.account.member.QMember member;

    public final NumberPath<Integer> projectArea = createNumber("projectArea", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> workEndDateTime = createDateTime("workEndDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> workStartDateTime = createDateTime("workStartDateTime", java.time.LocalDateTime.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.kosa.chanzipup.domain.account.company.QCompany(forProperty("company"), inits.get("company")) : null;
        this.estimate = inits.isInitialized("estimate") ? new com.kosa.chanzipup.domain.estimate.QEstimate(forProperty("estimate"), inits.get("estimate")) : null;
        this.member = inits.isInitialized("member") ? new com.kosa.chanzipup.domain.account.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}


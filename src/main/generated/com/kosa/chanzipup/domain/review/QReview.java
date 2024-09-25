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

    public final com.kosa.chanzipup.domain.buildingtype.QBuildingType buildingType;

    public final com.kosa.chanzipup.domain.account.company.QCompany company;

    public final StringPath content = createString("content");

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.chanzipup.domain.account.member.QMember member;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final ListPath<ReviewConstructionType, QReviewConstructionType> reviewConstructionTypes = this.<ReviewConstructionType, QReviewConstructionType>createList("reviewConstructionTypes", ReviewConstructionType.class, QReviewConstructionType.class, PathInits.DIRECT2);

    public final ListPath<ReviewImages, QReviewImages> reviewImages = this.<ReviewImages, QReviewImages>createList("reviewImages", ReviewImages.class, QReviewImages.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public final DatePath<java.time.LocalDate> workEndDate = createDate("workEndDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> workStartDate = createDate("workStartDate", java.time.LocalDate.class);

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
        this.buildingType = inits.isInitialized("buildingType") ? new com.kosa.chanzipup.domain.buildingtype.QBuildingType(forProperty("buildingType")) : null;
        this.company = inits.isInitialized("company") ? new com.kosa.chanzipup.domain.account.company.QCompany(forProperty("company"), inits.get("company")) : null;
        this.member = inits.isInitialized("member") ? new com.kosa.chanzipup.domain.account.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}


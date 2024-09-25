package com.kosa.chanzipup.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewConstructionType is a Querydsl query type for ReviewConstructionType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewConstructionType extends EntityPathBase<ReviewConstructionType> {

    private static final long serialVersionUID = 1305705410L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewConstructionType reviewConstructionType = new QReviewConstructionType("reviewConstructionType");

    public final com.kosa.chanzipup.domain.constructiontype.QConstructionType constructionType;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public QReviewConstructionType(String variable) {
        this(ReviewConstructionType.class, forVariable(variable), INITS);
    }

    public QReviewConstructionType(Path<? extends ReviewConstructionType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewConstructionType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewConstructionType(PathMetadata metadata, PathInits inits) {
        this(ReviewConstructionType.class, metadata, inits);
    }

    public QReviewConstructionType(Class<? extends ReviewConstructionType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.constructionType = inits.isInitialized("constructionType") ? new com.kosa.chanzipup.domain.constructiontype.QConstructionType(forProperty("constructionType")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}


package com.kosa.chanzipup.domain.estimate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstimatePrice is a Querydsl query type for EstimatePrice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimatePrice extends EntityPathBase<EstimatePrice> {

    private static final long serialVersionUID = 411687314L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstimatePrice estimatePrice = new QEstimatePrice("estimatePrice");

    public final QEstimateConstructionType constructionType;

    public final QEstimate estimate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QEstimatePrice(String variable) {
        this(EstimatePrice.class, forVariable(variable), INITS);
    }

    public QEstimatePrice(Path<? extends EstimatePrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstimatePrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstimatePrice(PathMetadata metadata, PathInits inits) {
        this(EstimatePrice.class, metadata, inits);
    }

    public QEstimatePrice(Class<? extends EstimatePrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.constructionType = inits.isInitialized("constructionType") ? new QEstimateConstructionType(forProperty("constructionType"), inits.get("constructionType")) : null;
        this.estimate = inits.isInitialized("estimate") ? new QEstimate(forProperty("estimate"), inits.get("estimate")) : null;
    }

}


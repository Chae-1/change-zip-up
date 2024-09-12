package com.kosa.chanzipup.domain.estimate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstimateConstructionType is a Querydsl query type for EstimateConstructionType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimateConstructionType extends EntityPathBase<EstimateConstructionType> {

    private static final long serialVersionUID = 1967014850L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstimateConstructionType estimateConstructionType = new QEstimateConstructionType("estimateConstructionType");

    public final com.kosa.chanzipup.domain.constructiontype.QConstructionType constructionType;

    public final QEstimate estimate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEstimateConstructionType(String variable) {
        this(EstimateConstructionType.class, forVariable(variable), INITS);
    }

    public QEstimateConstructionType(Path<? extends EstimateConstructionType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstimateConstructionType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstimateConstructionType(PathMetadata metadata, PathInits inits) {
        this(EstimateConstructionType.class, metadata, inits);
    }

    public QEstimateConstructionType(Class<? extends EstimateConstructionType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.constructionType = inits.isInitialized("constructionType") ? new com.kosa.chanzipup.domain.constructiontype.QConstructionType(forProperty("constructionType")) : null;
        this.estimate = inits.isInitialized("estimate") ? new QEstimate(forProperty("estimate"), inits.get("estimate")) : null;
    }

}


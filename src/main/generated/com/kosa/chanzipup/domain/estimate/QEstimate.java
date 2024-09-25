package com.kosa.chanzipup.domain.estimate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstimate is a Querydsl query type for Estimate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimate extends EntityPathBase<Estimate> {

    private static final long serialVersionUID = 329091575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstimate estimate = new QEstimate("estimate");

    public final com.kosa.chanzipup.domain.account.company.QCompany company;

    public final ListPath<EstimatePrice, QEstimatePrice> estimatePrices = this.<EstimatePrice, QEstimatePrice>createList("estimatePrices", EstimatePrice.class, QEstimatePrice.class, PathInits.DIRECT2);

    public final QEstimateRequest estimateRequest;

    public final EnumPath<EstimateStatus> estimateStatus = createEnum("estimateStatus", EstimateStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEstimate(String variable) {
        this(Estimate.class, forVariable(variable), INITS);
    }

    public QEstimate(Path<? extends Estimate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstimate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstimate(PathMetadata metadata, PathInits inits) {
        this(Estimate.class, metadata, inits);
    }

    public QEstimate(Class<? extends Estimate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.kosa.chanzipup.domain.account.company.QCompany(forProperty("company"), inits.get("company")) : null;
        this.estimateRequest = inits.isInitialized("estimateRequest") ? new QEstimateRequest(forProperty("estimateRequest"), inits.get("estimateRequest")) : null;
    }

}


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

    public final StringPath address = createString("address");

    public final StringPath budget = createString("budget");

    public final com.kosa.chanzipup.domain.buildingtype.QBuildingType buildingType;

    public final ListPath<EstimateConstructionType, QEstimateConstructionType> constructionTypes = this.<EstimateConstructionType, QEstimateConstructionType>createList("constructionTypes", EstimateConstructionType.class, QEstimateConstructionType.class, PathInits.DIRECT2);

    public final StringPath detailedAddress = createString("detailedAddress");

    public final StringPath estimateStatus = createString("estimateStatus");

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath identification = createString("identification");

    public final DatePath<java.time.LocalDate> measureDate = createDate("measureDate", java.time.LocalDate.class);

    public final com.kosa.chanzipup.domain.account.member.QMember member;

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final StringPath schedule = createString("schedule");

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
        this.buildingType = inits.isInitialized("buildingType") ? new com.kosa.chanzipup.domain.buildingtype.QBuildingType(forProperty("buildingType")) : null;
        this.member = inits.isInitialized("member") ? new com.kosa.chanzipup.domain.account.member.QMember(forProperty("member")) : null;
    }

}


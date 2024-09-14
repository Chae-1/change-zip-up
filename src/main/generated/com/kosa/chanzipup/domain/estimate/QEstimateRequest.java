package com.kosa.chanzipup.domain.estimate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstimateRequest is a Querydsl query type for EstimateRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimateRequest extends EntityPathBase<EstimateRequest> {

    private static final long serialVersionUID = 1905274008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstimateRequest estimateRequest = new QEstimateRequest("estimateRequest");

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

    public QEstimateRequest(String variable) {
        this(EstimateRequest.class, forVariable(variable), INITS);
    }

    public QEstimateRequest(Path<? extends EstimateRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstimateRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstimateRequest(PathMetadata metadata, PathInits inits) {
        this(EstimateRequest.class, metadata, inits);
    }

    public QEstimateRequest(Class<? extends EstimateRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buildingType = inits.isInitialized("buildingType") ? new com.kosa.chanzipup.domain.buildingtype.QBuildingType(forProperty("buildingType")) : null;
        this.member = inits.isInitialized("member") ? new com.kosa.chanzipup.domain.account.member.QMember(forProperty("member")) : null;
    }

}


package com.kosa.chanzipup.domain.account.company;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyConstructionType is a Querydsl query type for CompanyConstructionType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyConstructionType extends EntityPathBase<CompanyConstructionType> {

    private static final long serialVersionUID = -864016929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyConstructionType companyConstructionType = new QCompanyConstructionType("companyConstructionType");

    public final QCompany company;

    public final com.kosa.chanzipup.domain.constructiontype.QConstructionType constructionType;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCompanyConstructionType(String variable) {
        this(CompanyConstructionType.class, forVariable(variable), INITS);
    }

    public QCompanyConstructionType(Path<? extends CompanyConstructionType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyConstructionType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyConstructionType(PathMetadata metadata, PathInits inits) {
        this(CompanyConstructionType.class, metadata, inits);
    }

    public QCompanyConstructionType(Class<? extends CompanyConstructionType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company"), inits.get("company")) : null;
        this.constructionType = inits.isInitialized("constructionType") ? new com.kosa.chanzipup.domain.constructiontype.QConstructionType(forProperty("constructionType")) : null;
    }

}


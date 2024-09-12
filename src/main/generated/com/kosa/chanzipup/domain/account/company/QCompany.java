package com.kosa.chanzipup.domain.account.company;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -560380396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final com.kosa.chanzipup.domain.account.QAccount _super;

    //inherited
    public final EnumPath<com.kosa.chanzipup.domain.account.AccountRole> accountRole;

    public final StringPath address = createString("address");

    public final StringPath companyDesc = createString("companyDesc");

    public final StringPath companyLogoUrl = createString("companyLogoUrl");

    public final StringPath companyName = createString("companyName");

    public final StringPath companyNumber = createString("companyNumber");

    public final ListPath<CompanyConstructionType, QCompanyConstructionType> constructionTypes = this.<CompanyConstructionType, QCompanyConstructionType>createList("constructionTypes", CompanyConstructionType.class, QCompanyConstructionType.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime;

    //inherited
    public final StringPath email;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isVerified;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedDateTime;

    public final StringPath owner = createString("owner");

    //inherited
    public final StringPath password;

    //inherited
    public final StringPath phoneNumber;

    public final DatePath<java.time.LocalDate> publishDate = createDate("publishDate", java.time.LocalDate.class);

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    // inherited
    public final com.kosa.chanzipup.domain.account.token.QRefreshToken refreshToken;

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.kosa.chanzipup.domain.account.QAccount(type, metadata, inits);
        this.accountRole = _super.accountRole;
        this.createdDateTime = _super.createdDateTime;
        this.email = _super.email;
        this.id = _super.id;
        this.isVerified = _super.isVerified;
        this.lastUpdatedDateTime = _super.lastUpdatedDateTime;
        this.password = _super.password;
        this.phoneNumber = _super.phoneNumber;
        this.refreshToken = _super.refreshToken;
    }

}


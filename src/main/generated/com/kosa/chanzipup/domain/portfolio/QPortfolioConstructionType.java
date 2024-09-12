package com.kosa.chanzipup.domain.portfolio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortfolioConstructionType is a Querydsl query type for PortfolioConstructionType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPortfolioConstructionType extends EntityPathBase<PortfolioConstructionType> {

    private static final long serialVersionUID = 1222161974L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortfolioConstructionType portfolioConstructionType = new QPortfolioConstructionType("portfolioConstructionType");

    public final com.kosa.chanzipup.domain.constructiontype.QConstructionType constructionType;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPortfolio portfolio;

    public QPortfolioConstructionType(String variable) {
        this(PortfolioConstructionType.class, forVariable(variable), INITS);
    }

    public QPortfolioConstructionType(Path<? extends PortfolioConstructionType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortfolioConstructionType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortfolioConstructionType(PathMetadata metadata, PathInits inits) {
        this(PortfolioConstructionType.class, metadata, inits);
    }

    public QPortfolioConstructionType(Class<? extends PortfolioConstructionType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.constructionType = inits.isInitialized("constructionType") ? new com.kosa.chanzipup.domain.constructiontype.QConstructionType(forProperty("constructionType")) : null;
        this.portfolio = inits.isInitialized("portfolio") ? new QPortfolio(forProperty("portfolio"), inits.get("portfolio")) : null;
    }

}


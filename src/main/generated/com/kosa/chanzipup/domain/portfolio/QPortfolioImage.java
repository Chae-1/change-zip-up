package com.kosa.chanzipup.domain.portfolio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortfolioImage is a Querydsl query type for PortfolioImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPortfolioImage extends EntityPathBase<PortfolioImage> {

    private static final long serialVersionUID = 706305968L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortfolioImage portfolioImage = new QPortfolioImage("portfolioImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QPortfolio portfolio;

    public QPortfolioImage(String variable) {
        this(PortfolioImage.class, forVariable(variable), INITS);
    }

    public QPortfolioImage(Path<? extends PortfolioImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortfolioImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortfolioImage(PathMetadata metadata, PathInits inits) {
        this(PortfolioImage.class, metadata, inits);
    }

    public QPortfolioImage(Class<? extends PortfolioImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.portfolio = inits.isInitialized("portfolio") ? new QPortfolio(forProperty("portfolio"), inits.get("portfolio")) : null;
    }

}


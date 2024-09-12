package com.kosa.chanzipup.domain.constructiontype;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConstructionType is a Querydsl query type for ConstructionType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConstructionType extends EntityPathBase<ConstructionType> {

    private static final long serialVersionUID = 375699575L;

    public static final QConstructionType constructionType = new QConstructionType("constructionType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QConstructionType(String variable) {
        super(ConstructionType.class, forVariable(variable));
    }

    public QConstructionType(Path<? extends ConstructionType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConstructionType(PathMetadata metadata) {
        super(ConstructionType.class, metadata);
    }

}


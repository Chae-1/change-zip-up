package com.kosa.chanzipup.domain.buildingtype;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBuildingType is a Querydsl query type for BuildingType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuildingType extends EntityPathBase<BuildingType> {

    private static final long serialVersionUID = -1313082409L;

    public static final QBuildingType buildingType = new QBuildingType("buildingType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QBuildingType(String variable) {
        super(BuildingType.class, forVariable(variable));
    }

    public QBuildingType(Path<? extends BuildingType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBuildingType(PathMetadata metadata) {
        super(BuildingType.class, metadata);
    }

}


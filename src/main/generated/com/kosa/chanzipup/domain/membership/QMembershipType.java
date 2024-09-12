package com.kosa.chanzipup.domain.membership;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMembershipType is a Querydsl query type for MembershipType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMembershipType extends EntityPathBase<MembershipType> {

    private static final long serialVersionUID = -1033645295L;

    public static final QMembershipType membershipType = new QMembershipType("membershipType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MembershipName> name = createEnum("name", MembershipName.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QMembershipType(String variable) {
        super(MembershipType.class, forVariable(variable));
    }

    public QMembershipType(Path<? extends MembershipType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMembershipType(PathMetadata metadata) {
        super(MembershipType.class, metadata);
    }

}


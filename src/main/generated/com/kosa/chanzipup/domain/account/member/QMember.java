package com.kosa.chanzipup.domain.account.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 922594486L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.kosa.chanzipup.domain.account.QAccount _super;

    //inherited
    public final EnumPath<com.kosa.chanzipup.domain.account.AccountRole> accountRole;

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

    public final EnumPath<MemberType> memberType = createEnum("memberType", MemberType.class);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    //inherited
    public final StringPath password;

    //inherited
    public final StringPath phoneNumber;

    // inherited
    public final com.kosa.chanzipup.domain.account.token.QRefreshToken refreshToken;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
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


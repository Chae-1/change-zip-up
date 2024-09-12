package com.kosa.chanzipup.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 1690483765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final com.kosa.chanzipup.domain.QBaseEntity _super = new com.kosa.chanzipup.domain.QBaseEntity(this);

    public final EnumPath<AccountRole> accountRole = createEnum("accountRole", AccountRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isVerified = createBoolean("isVerified");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedDateTime = _super.lastUpdatedDateTime;

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final com.kosa.chanzipup.domain.account.token.QRefreshToken refreshToken;

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.refreshToken = inits.isInitialized("refreshToken") ? new com.kosa.chanzipup.domain.account.token.QRefreshToken(forProperty("refreshToken")) : null;
    }

}


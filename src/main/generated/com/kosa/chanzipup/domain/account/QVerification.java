package com.kosa.chanzipup.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVerification is a Querydsl query type for Verification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVerification extends EntityPathBase<Verification> {

    private static final long serialVersionUID = -638801645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVerification verification = new QVerification("verification");

    public final QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath verificationCode = createString("verificationCode");

    public QVerification(String variable) {
        this(Verification.class, forVariable(variable), INITS);
    }

    public QVerification(Path<? extends Verification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVerification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVerification(PathMetadata metadata, PathInits inits) {
        this(Verification.class, metadata, inits);
    }

    public QVerification(Class<? extends Verification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
    }

}


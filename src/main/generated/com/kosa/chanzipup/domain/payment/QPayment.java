package com.kosa.chanzipup.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 499065575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final com.kosa.chanzipup.domain.QBaseEntity _super = new com.kosa.chanzipup.domain.QBaseEntity(this);

    public final com.kosa.chanzipup.domain.account.company.QCompany company;

    public final DateTimePath<java.time.LocalDateTime> completeDate = createDateTime("completeDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedDateTime = _super.lastUpdatedDateTime;

    public final com.kosa.chanzipup.domain.membership.QMembershipType membershipType;

    public final StringPath merchantUid = createString("merchantUid");

    public final DateTimePath<java.time.LocalDateTime> requestDate = createDateTime("requestDate", java.time.LocalDateTime.class);

    public final EnumPath<PaymentStatus> status = createEnum("status", PaymentStatus.class);

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.kosa.chanzipup.domain.account.company.QCompany(forProperty("company"), inits.get("company")) : null;
        this.membershipType = inits.isInitialized("membershipType") ? new com.kosa.chanzipup.domain.membership.QMembershipType(forProperty("membershipType")) : null;
    }

}


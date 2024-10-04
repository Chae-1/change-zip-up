package com.kosa.chanzipup.api.admin.service.membership.query;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.membership.QMembership.membership;
import static com.kosa.chanzipup.domain.membership.QMembershipType.membershipType;
import static com.kosa.chanzipup.domain.payment.QPayment.payment;

import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.payment.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminMembershipQueryRepository {

    private final JPAQueryFactory factory;

    public List<Membership> getAllMembershipCompany() {
        return factory.selectFrom(membership)
                .leftJoin(membership.payment, payment).fetchJoin()
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .leftJoin(membership.company, company).fetchJoin()
                .orderBy(membership.startDateTime.desc())
                .fetch();
    }

    public Membership findByIdWithPayment(Long membershipId) {
        return factory.selectFrom(membership)
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .leftJoin(membership.payment, payment).fetchJoin()
                .where(membership.id.eq(membershipId))
                .fetchOne();
    }
}

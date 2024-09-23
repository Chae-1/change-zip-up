package com.kosa.chanzipup.api.admin.service.membership.query;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.membership.QMembership.membership;
import static com.kosa.chanzipup.domain.membership.QMembershipType.membershipType;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminMembershipQueryRepository {

    private final JPAQueryFactory factory;

    public void getAllMembershipCompany() {
        factory.selectFrom(membership)
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .leftJoin(membership.company, company).fetchJoin();

    }

}

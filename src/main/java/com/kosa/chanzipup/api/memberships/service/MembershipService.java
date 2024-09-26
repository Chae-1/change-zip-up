package com.kosa.chanzipup.api.memberships.service;

import com.kosa.chanzipup.api.memberships.controller.response.MembershipResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyException;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.membership.Membership;
import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
import com.kosa.chanzipup.domain.membership.MembershipRepository;
import com.kosa.chanzipup.domain.payment.Payment;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import com.kosa.chanzipup.domain.payment.PaymentResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MembershipService {

    private static final int MEMBERSHIP_EXPIRE_DAY = 31;

    private final CompanyRepository companyRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;


    @Transactional
    public MembershipResponse registerMembership(PaymentResult paymentResult) {
        // 1. membership을 가입할 수 없다.
        Company company = companyRepository.findById(paymentResult.getCompanyId())
                .orElseThrow(() -> new CompanyException("존재하지 않는 회사 정보입니다."));
        MembershipType membershipType = membershipTypeRepository.findById(paymentResult.getMembershipTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버십 정보입니다."));
        Payment payment = paymentRepository.findByImpUid(paymentResult.getImpUid())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));

        // todo : 기존에 가입한 멤버십이 있다면?

        // 2. 가입 한 멤버십은 MEMBERSHIP_EXPIRE_DAY 만큼 유지된다.
        Membership membership = createMembership(company, membershipType, payment);
        membershipRepository.save(membership);

        return MembershipResponse.of(membershipType.getName().name(), membershipType.getPrice(),
                membership.getStartDateTime(), membership.getEndDateTime());
    }


    // 2. 가입 한 멤버십은 MEMBERSHIP_EXPIRE_DAY 만큼 유지된다.
    private Membership createMembership(Company company, MembershipType membershipType, Payment payment) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredDateTime = now.plusDays(MEMBERSHIP_EXPIRE_DAY);

        return Membership.ofNewMembership(company, membershipType, now, expiredDateTime, payment);
    }

    public void getAllMembershipHistories(String email) {
//        membership
    }
}

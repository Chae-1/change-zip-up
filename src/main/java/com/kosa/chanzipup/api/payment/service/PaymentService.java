package com.kosa.chanzipup.api.payment.service;

import com.kosa.chanzipup.api.payment.controller.response.PaymentPrepareResponse;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternalRepository;
import com.kosa.chanzipup.domain.payment.Payment;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MembershipInternalRepository membershipInternalRepository;

    @Transactional
    public PaymentPrepareResponse createNewPayment(String email, Long membershipId) {
        // 1. 고객이 선택한 멤버십을 조회한다.
        MembershipInternal membershipInternal = membershipInternalRepository.findById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버십입니다."));

        // 2. 새로운 결제 정보를 생성한다.
        Payment payment = Payment.create(membershipInternal, LocalDateTime.now());
        paymentRepository.save(payment);

        // 3. 고객에게 결제에 대한 정보(merchant_id를 포함한 정보), 회사의 기본 정보를 전달한다.
        // todo: 회사 정보까지 Dto로 전달.

        return PaymentPrepareResponse.of(payment.getMerchantUid(), membershipInternal.getType());
    }
}

package com.kosa.chanzipup.api.payment.service;

import com.kosa.chanzipup.api.payment.controller.request.PaymentResult;
import com.kosa.chanzipup.api.payment.controller.response.PaymentPrepareResponse;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternal;
import com.kosa.chanzipup.domain.membershipinternal.MembershipInternalRepository;
import com.kosa.chanzipup.domain.payment.Payment;
import com.kosa.chanzipup.domain.payment.PaymentRepository;
import com.kosa.chanzipup.domain.payment.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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

    // 성공 시: payment 정보를 조회해서 결과를 업데이트 한다.

    // 실패 시: 저장하고 있던 payment 정보를 삭제한다.

    // -> 이전에 수행중이었던 결제를 처리한다.
    // -> 이를 관리하고 있어도 의미가 없기 때문이다.
    
    // todo: 향후, 어떤 DTO를 반환해야하는지 논의가 필요함
//    public boolean processPayment(PaymentResult paymentResult) {
//        Payment payment = paymentRepository.findByMerchantUid(paymentResult.getMerchantUid())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));
//
//        LocalDateTime completeDate = LocalDateTime.now();
//        boolean isSuccess = paymentResult.isSuccess(); // 결제 성공 여부
//        if (isSuccess) {
//            payment.success(paymentResult, completeDate);
//            return true;
//        }
//
//        // 사용자가 결제를 취소하면, 서버에 있는 결제 내역을 삭제한다.
//        handleFailedPayment(payment);
//        return false;
//    }

    private void handleFailedPayment(Payment payment) {
        paymentRepository.delete(payment);
    }

    @Transactional
    public String cancelPayment(Long paymentId) {
        log.info("paymentId = {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));
        payment.cancel();
        return payment.getImpUid();
    }

    @Transactional
    public boolean processPayment(String impUid, String merchantUid,
                                  int paidAmount, boolean isSuccess) {
        Payment payment = paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));


        LocalDateTime completeDate = LocalDateTime.now();
        // 실제 pg사를 통해 결제가 처리되었을 경우 isSuccess == true
        if (isSuccess) {
            payment.success(impUid, paidAmount, completeDate);
            return true;
        }

        // 사용자가 결제를 취소하면, 서버에 있는 결제 내역을 삭제한다.
        handleFailedPayment(payment);
        return false;
    }
}
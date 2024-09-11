package com.kosa.chanzipup.api.payment.service;

import com.kosa.chanzipup.api.payment.controller.response.PaymentPrepareResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyException;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.membership.MembershipType;
import com.kosa.chanzipup.domain.membership.MembershipTypeRepository;
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
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CompanyRepository companyRepository;

    private final MembershipTypeRepository membershipTypeRepository;

    @Transactional
    public PaymentPrepareResponse createNewPayment(String email, Long membershipId) {
        // 1. 고객이 선택한 멤버십을 조회한다.
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new CompanyException("존재하지 않는 회사 정보입니다."));

        MembershipType membershipType = membershipTypeRepository.findById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버십입니다."));

        // 2. 새로운 결제 정보를 생성한다.
        Payment payment = Payment.create(membershipType, company, LocalDateTime.now());
        paymentRepository.save(payment);

        // 3. 고객에게 결제에 대한 정보(merchant_id를 포함한 정보), 회사의 기본 정보를 전달한다.
        // todo: 회사 정보까지 Dto로 전달.

        return PaymentPrepareResponse.of(payment.getMerchantUid(), membershipType.getName(), company);
    }

    // 성공 시: payment 정보를 조회해서 결과를 업데이트 한다.
    // 실패 시: 저장하고 있던 payment 정보를 삭제한다.
    // -> 이전에 수행중이었던 결제를 처리한다.
    // -> 이를 관리하고 있어도 의미가 없기 때문이다.

    // todo: 향후, 어떤 DTO를 반환해야하는지 논의가 필요함

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

    // 실제 결제한 멤버십 아이디, 성공 여부를 반환 한다.
    @Transactional
    public PaymentResult processPayment(String impUid, String merchantUid,
                                        Integer paidAmount, boolean isSuccess, String email) {

        Payment payment = paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));
        MembershipType membershipType = payment.getMembershipType();
        Company company = payment.getCompany();

        LocalDateTime completeDate = LocalDateTime.now();
        // 실제 pg사를 통해 결제가 처리되었을 경우 isSuccess == true
        if (isSuccess) {
            payment.success(impUid, paidAmount, completeDate);
            return PaymentResult.of(true, membershipType.getId(), company.getId());
        }

        // 사용자가 결제를 취소하면, 서버에 있는 결제 내역을 삭제한다.
        handleFailedPayment(payment);
        // throw new PaymentException("결재에 실패하였습니다.");
        return PaymentResult.of(false, membershipType.getId(), company.getId());
    }
}

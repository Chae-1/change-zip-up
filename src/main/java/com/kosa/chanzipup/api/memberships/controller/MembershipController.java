package com.kosa.chanzipup.api.memberships.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.memberships.controller.response.MembershipResponse;
import com.kosa.chanzipup.api.memberships.service.MembershipService;
<<<<<<< login-feature
import com.kosa.chanzipup.api.payment.controller.request.PaymentConfirmation;
import com.kosa.chanzipup.api.payment.service.PaymentService;
import com.kosa.chanzipup.domain.membership.MembershipRegisterException;
import com.kosa.chanzipup.domain.membershipinternal.MembershipId;
import com.kosa.chanzipup.domain.payment.PaymentResult;
=======
import com.kosa.chanzipup.api.payment.controller.request.PaymentResult;
import com.kosa.chanzipup.api.payment.service.PaymentService;
import com.kosa.chanzipup.domain.membershipinternal.MembershipId;
>>>>>>> main
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memberships")
@Slf4j
public class MembershipController {

<<<<<<< login-feature
    private final MembershipService membershipService;
    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<MembershipResponse> subscribeToMembership(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestBody @Valid PaymentConfirmation paymentResult) {
        // 1. 생성했던 결제 정보를 처리한다.(성공 시, 실패 여부를 반환)
        PaymentResult processResult = paymentService.processPayment(paymentResult.getImpUid(), paymentResult.getMerchantUid(),
                paymentResult.getPaidAmount(), paymentResult.getSuccess(), userDetails.getUsername());

        // 2. 성공적으로 결제하면 이때부터 한달 간, 멤버십에 가입된다.
        // 실패하면 예외가 발생한다.
        if (!processResult.isSuccess()) {
            throw new MembershipRegisterException("멤버십 등록에 실패하였습니다.");
        }
        return ApiResponse.ok(membershipService.registerMembership(processResult));
    }

    @GetMapping("/{membershipId}")
    public void getMembership(@PathVariable MembershipId membershipId) {
        log.info("membershipId = {}", membershipId.getMembershipId());
    }
=======
    private MembershipService membershipService;
    private PaymentService paymentService;

    @PostMapping
    public void subscribeToMembership(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestBody @Valid PaymentResult paymentResult) {
        // 1. Role_USER
        log.info("회원은 반드시, 기업 회원이어야한다.");
    }

    @GetMapping("/{membershipId}")
    public void getMembership(@PathVariable MembershipId membershipId) {
        log.info("membershipId = {}", membershipId.getMembershipId());
    }
>>>>>>> main
}
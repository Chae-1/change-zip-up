package com.kosa.chanzipup.api.memberships.controller;

import com.kosa.chanzipup.api.memberships.service.MembershipService;
import com.kosa.chanzipup.api.payment.controller.request.PaymentResult;
import com.kosa.chanzipup.api.payment.service.PaymentService;
import com.kosa.chanzipup.domain.membershipinternal.MembershipId;
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
}
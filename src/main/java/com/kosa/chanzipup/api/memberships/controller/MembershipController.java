package com.kosa.chanzipup.api.memberships.controller;

import com.kosa.chanzipup.api.memberships.service.MembershipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
@Slf4j
public class MembershipController {

    private MembershipService membershipService;

    @PostMapping
    public void subscribeToMembership(@AuthenticationPrincipal UserDetails userDetails) {
        // void
    }
}

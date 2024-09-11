package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.service.EstimateService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimate")
@Slf4j
public class EstimateController {

    private final EstimateService estimateService;

    @GetMapping
    public void test(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        String email = userDetails.getUsername(); //
        estimateService.findAll(email);
    }
}

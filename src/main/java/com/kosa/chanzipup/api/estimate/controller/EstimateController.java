package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequest;
import com.kosa.chanzipup.api.estimate.service.EstimateService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimate")
@Slf4j
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/request")
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequest estimateRequest
            , @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        // JWT에서 이메일 추출
        String email = userDetails.getUsername();

        // Estimate 생성
        estimateService.createEstimate(estimateRequest, email);

        return ResponseEntity.ok("Estimate has been created.");
    }

//    @GetMapping
//    public void test(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
//        String email = userDetails.getUsername();
//        estimateService.findAll(email);
//    }
}

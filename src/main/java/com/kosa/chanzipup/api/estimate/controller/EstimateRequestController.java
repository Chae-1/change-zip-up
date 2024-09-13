package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.service.EstimateRequestService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimate")
@Slf4j
public class EstimateRequestController {

    private final EstimateRequestService estimateRequestService;

    @PostMapping("/request")
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequestDTO estimateRequestDTO
            , @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        // JWT에서 이메일 추출
        String email = userDetails.getUsername();

        // Estimate 생성
        estimateRequestService.createEstimate(estimateRequestDTO, email);

        return ResponseEntity.ok("Estimate has been created.");
    }

//    @GetMapping
//    public void test(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
//        String email = userDetails.getUsername();
//        estimateService.findAll(email);
//    }
}

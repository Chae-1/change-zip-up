package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResult;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.estimate.EstimateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/estimates")
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping
    public ApiResponse<EstimateResult> sendEstimateToCompany(@RequestBody EstimateRegisterRequest request,
                                             @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        // 견적 요청 사용자
        String userEamil = userDetails.getUsername();
        EstimateResult estimateResult = estimateService.sendEstimateToCompany(userEamil, request);
        return ApiResponse.ok(estimateResult);
    }

}

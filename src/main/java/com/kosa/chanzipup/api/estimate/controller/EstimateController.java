package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResult;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.estimate.EstimateRequest;
import com.kosa.chanzipup.api.estimate.service.EstimateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/estimates")
public class EstimateController {

    private final EstimateService estimateService;

    @PostMapping("/send")
    public ApiResponse<EstimateResult> sendEstimateToCompany(@RequestBody EstimateRegisterRequest request,
                                             @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        // 견적 요청 사용자
        String userEmail = userDetails.getUsername();
        EstimateResult estimateResult = estimateService.sendEstimateToCompany(userEmail, request);
        return ApiResponse.ok(estimateResult);
    }

    @GetMapping("/request/latest")
    public ApiResponse<Long> getLatestEstimateRequest(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        EstimateRequest latestEstimateRequest = estimateService.getLatestEstimateRequestByUserEmail(userEmail);
        return ApiResponse.ok(latestEstimateRequest.getId());
    }

    @GetMapping("/list")
    public ApiResponse<List<EstimateResult>> getEstimateList(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        // 견적 요청을 받은 업체
        String companyEmail = userDetails.getUsername();

        // 해당 업체에게 온 요청만 조회한다
        List<EstimateResult> estimateList = estimateService.getEstimatesByCompanyEmail(companyEmail);
        return ApiResponse.ok(estimateList);
    }

}

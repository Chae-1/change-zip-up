package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResult;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.company.CompanyId;
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
        // 견적 요청을 받은 업체.
        String companyEmail = userDetails.getUsername();

        // 해당 업체에게 온 요청만 조회한다.
        List<EstimateResult> estimateList = estimateService.getWaitingEstimatesByCompanyEmail(companyEmail);
        return ApiResponse.ok(estimateList);
    }

    @PostMapping("/{estimateRequestId}/cancel")
    public ApiResponse<Void> cancelEstimate(@PathVariable Long estimateRequestId,
                                            @AuthenticationPrincipal UnifiedUserDetails userDetails,
                                            @RequestBody CompanyId companyId) {
        // 로그인한 업체 이메일을 가져옴
        String memberEmail = userDetails.getUsername();

        // 서비스 호출 시 estimateRequestId와 companyEmail을 넘김
        estimateService.rejectEstimateByMember(estimateRequestId, memberEmail, companyId.getCompanyId());
        return ApiResponse.ok(null);
    }

    @PostMapping("/approval/{estimateRequestId}")
    public ApiResponse<Void> approvalEstimate(@PathVariable Long estimateRequestId,
                                            @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        // 로그인한 업체 이메일을 가져옴
        String companyEmail = userDetails.getUsername();

        // 서비스 호출 시 estimateRequestId와 companyEmail을 넘김
        estimateService.approvalEstimateByRequestIdAndCompanyEmail(estimateRequestId, companyEmail);
        return ApiResponse.ok(null);
    }


}

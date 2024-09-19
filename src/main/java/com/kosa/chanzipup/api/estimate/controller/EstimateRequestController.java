package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestResponse;
import com.kosa.chanzipup.api.estimate.service.EstimateRequestService;
import com.kosa.chanzipup.api.estimate.service.query.EstimateRequestQueryService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimate")
@Slf4j
public class EstimateRequestController {

    private final EstimateRequestService estimateRequestService;
    private final EstimateRequestQueryService queryService;

    @PostMapping("/request")
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequestDTO estimateRequestDTO
            , @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        // JWT에서 이메일 추출
        String email = userDetails.getUsername();

        // Estimate 생성
        estimateRequestService.createEstimate(estimateRequestDTO, email);


        return ResponseEntity.ok("Estimate has been created.");
    }


    @GetMapping("/requests")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateRequestResponse>> getAllEstimateRequests(){
        List<EstimateRequestResponse> estimateRequestResponses = queryService.estimateRequestResponses();
        return ResponseEntity.ok(estimateRequestResponses);
    }
}

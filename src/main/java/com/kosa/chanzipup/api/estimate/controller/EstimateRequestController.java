package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimatePriceRequest;
import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateConstructionResponse;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateDetailResponse;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestResponse;
import com.kosa.chanzipup.api.estimate.controller.response.SimpleEstimateResponse;
import com.kosa.chanzipup.api.estimate.service.EstimateRequestService;
import com.kosa.chanzipup.api.estimate.service.EstimateService;
import com.kosa.chanzipup.api.estimate.service.query.EstimateQueryService;
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
@RequestMapping("/api/estimaterequests")
@Slf4j
public class EstimateRequestController {

    private final EstimateRequestService estimateRequestService;
    private final EstimateQueryService queryService;
    private final EstimateService estimateService;

    @PostMapping
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequestDTO estimateRequestDTO,
                                            @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        // JWT에서 이메일 추출
        String email = userDetails.getUsername();

        // Estimate 생성
        estimateRequestService.createEstimate(estimateRequestDTO, email);
        return ResponseEntity.ok("Estimate has been created.");
    }

    @GetMapping
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateRequestResponse>> getAllEstimateRequests(@AuthenticationPrincipal UnifiedUserDetails userDetails){
        List<EstimateRequestResponse> estimateRequestResponses = queryService.getEstimateRequestResponsesOnWaiting(userDetails.getUsername());
        return ResponseEntity.ok(estimateRequestResponses);
    }

    @GetMapping("/received")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateRequestResponse>> getAllReceivedEstimate(@AuthenticationPrincipal UnifiedUserDetails userDetails){
        List<EstimateRequestResponse> estimateRequestResponses = queryService.getAllReceivedEstimate(userDetails.getUsername());
        return ResponseEntity.ok(estimateRequestResponses);
    }


    @GetMapping("/{estimateRequestId}/write")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateConstructionResponse>> getEstimatePriceDetail(@PathVariable Long estimateRequestId) {
        return ResponseEntity.ok(queryService.getEstimatePriceDetail(estimateRequestId));
    }


    @PostMapping("/{estimateRequestId}/write")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<Void> writeEstimatePrices(@PathVariable Long estimateRequestId,
                                                    @RequestBody EstimatePriceRequest request,
                                                    @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        String email = userDetails.getUsername();
        estimateRequestService.writePricesOfNewEstimate(email, estimateRequestId, request.getConstructionPrices());
        return ResponseEntity.ok(null);

    }

    @GetMapping("/users")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<List<EstimateRequestResponse>> findAllUserReceivedRequests(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(queryService.getAllEstimateRequestByUser(userDetails.getUsername()));
    }


    @GetMapping("/{requestId}/estimates")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<SimpleEstimateResponse>> getAllEstimateOnEstimateRequest(@PathVariable Long requestId,
                                                                                        @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        return ResponseEntity.ok(queryService.findAllEstimateSimpleOnEstimateRequest(requestId));
    }


    @GetMapping("/{requestId}/estimates/{estimateId}")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<EstimateDetailResponse> getAllEstimateOnEstimateRequest(@PathVariable Long requestId,
                                                                                  @PathVariable Long estimateId,
                                                                                  @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(queryService.getEstimateDetail(requestId, estimateId));
    }


    @PostMapping("/{requestId}/estimates/{estimateId}/accept")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<Void> acceptEstimate(@PathVariable Long requestId,
                                               @PathVariable Long estimateId,
                                               @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        estimateService.acceptEstimate(requestId, estimateId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{requestId}/estimates/{estimateId}/reject")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<Void> rejectEstimate(@PathVariable Long requestId,
                                               @PathVariable Long estimateId,
                                               @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        estimateService.rejectEstimate(requestId, estimateId);
        return ResponseEntity.ok(null);

    }


}

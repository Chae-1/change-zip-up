package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimatePriceRequest;
import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.controller.response.*;
import com.kosa.chanzipup.api.estimate.service.EstimateRequestService;
import com.kosa.chanzipup.api.estimate.service.EstimateService;
import com.kosa.chanzipup.api.estimate.service.query.EstimateQueryService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.estimate.EstimateRequestStatus;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
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
    public ResponseEntity<EstimateRequestCreateResponse> createEstimateRequest(
            @RequestBody EstimateRequestDTO estimateRequestDTO,
            @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        // JWT에서 이메일 추출
        String email = userDetails.getUsername();

        // Estimate 생성
        return ResponseEntity.ok(estimateRequestService.createEstimateRequest(estimateRequestDTO, email));
    }

    @GetMapping
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateRequestResponse>> getAllEstimateRequests(
            @AuthenticationPrincipal UnifiedUserDetails userDetails,
            @RequestParam(value = "status") EstimateRequestStatus status) {
        List<EstimateRequestResponse> estimateRequestResponses = queryService.getEstimateRequestResponsesOn(
                userDetails.getUsername(), status);
        return ResponseEntity.ok(estimateRequestResponses);
    }

    @GetMapping("/sent")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateResponse>> getAllSentEstimate(
            @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        List<EstimateResponse> estimateRequestResponses = queryService.getAllSentEstimate(
                userDetails.getUsername());
        return ResponseEntity.ok(estimateRequestResponses);
    }

    @GetMapping("/received")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateRequestResponse>> getAllReceivedEstimate(
            @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        List<EstimateRequestResponse> estimateRequestResponses = queryService.getAllReceivedEstimate(
                userDetails.getUsername());
        return ResponseEntity.ok(estimateRequestResponses);
    }


    @GetMapping("/{estimateRequestId}/write")
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity<List<EstimateConstructionResponse>> getEstimatePriceDetail(
            @PathVariable Long estimateRequestId) {
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

    @PostMapping("/{estimateRequestId}/cancel")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<Boolean> cancelEstimateRequest(@PathVariable Long estimateRequestId,
                                                         @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        estimateRequestService.cancelRequest(estimateRequestId);

        return ResponseEntity.ok(true);
    }


    @PostMapping("/{estimateRequestId}/complete")
    public ResponseEntity<Boolean> completeEstimateRequest(@PathVariable Long estimateRequestId,
                                                           @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        estimateRequestService.completeRequest(estimateRequestId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/users")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<List<EstimateRequestResponse>> findAllUserReceivedRequests(
            @AuthenticationPrincipal UnifiedUserDetails userDetails,
            @RequestParam("status") EstimateRequestStatus status) {
        return ResponseEntity.ok(queryService.getAllEstimateRequestByUser(userDetails.getUsername(), status));
    }

    @GetMapping("/users/complete/{requestId}")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<EstimateDetailResponse> findAllUserCompleteRequests(
            @PathVariable("requestId") Long requestId,
            @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(queryService.getCompleteEstimateOnRequest(requestId));
    }


    @GetMapping("/{requestId}/estimates/sent")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<List<SimpleEstimateResponse>> getAllEstimateOnEstimateRequest(@PathVariable Long requestId,
                                                                                        @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        return ResponseEntity.ok(queryService.findAllEstimateSimpleOnEstimateRequest(requestId));
    }

    @GetMapping("/{requestId}/estimates/accept")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<EstimateDetailResponse> getAcceptedEstimateOnEstimateRequest(@PathVariable Long requestId,
                                                                                       @AuthenticationPrincipal UnifiedUserDetails userDetails) {

        return ResponseEntity.ok(queryService.getAcceptedEstimateDetailOf(requestId));
    }


    @GetMapping("/{requestId}/estimates/{estimateId}")
    @PreAuthorize("ROLE_USER")
    public ResponseEntity<EstimateDetailResponse> getAllEstimateOnEstimateRequest(@PathVariable Long requestId,
                                                                                  @PathVariable Long estimateId,
                                                                                  @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(queryService.getEstimateDetailOf(requestId, estimateId,
                EstimateStatus.SENT, EstimateRequestStatus.WAITING));
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

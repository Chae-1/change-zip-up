package com.kosa.chanzipup.api.estimate.controller;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.estimate.EstimateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EstimateController {

    private final EstimateService estimateService;

    public void sendEstimateToCompany(@RequestBody EstimateRegisterRequest request,
                                      @AuthenticationPrincipal UnifiedUserDetails userDetails) {
        // 견적 요청 사용자
        String userEamil = userDetails.getUsername();
        
    }

}

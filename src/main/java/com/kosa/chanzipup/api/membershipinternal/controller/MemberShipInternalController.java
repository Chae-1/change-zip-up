package com.kosa.chanzipup.api.membershipinternal.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.membershipinternal.controller.response.MemberShipInternalResponse;
import com.kosa.chanzipup.api.membershipinternal.service.MemberShipInternalService;
import java.util.List;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/membershiptypes")
public class MemberShipInternalController {

    private final MemberShipInternalService memberShipInternalService;

    // Query
    @GetMapping
    public ApiResponse<List<MemberShipInternalResponse>> getAllMemberShips() {
        return ApiResponse.ok(memberShipInternalService.getAllMemberShips());
    }



}

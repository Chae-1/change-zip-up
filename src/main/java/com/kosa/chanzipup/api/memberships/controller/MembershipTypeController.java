package com.kosa.chanzipup.api.memberships.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.member.controller.response.MembershipTypeResponse;
import com.kosa.chanzipup.api.memberships.service.MembershipTypeService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/membershiptypes")
public class MembershipTypeController {

    private final MembershipTypeService membershipTypeService;

    @GetMapping
    public ApiResponse<List<MembershipTypeResponse>> getAllMemberShips() {
        return ApiResponse.ok(membershipTypeService.getAllMemberShips());
    }
}

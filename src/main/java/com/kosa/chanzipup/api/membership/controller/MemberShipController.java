package com.kosa.chanzipup.api.membership.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.membership.controller.response.MemberShipResponse;
import com.kosa.chanzipup.api.membership.service.MemberShipService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memberships")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @GetMapping
    public ApiResponse<List<MemberShipResponse>> getAllMemberShips() {
        return ApiResponse.ok(memberShipService.getAllMemberShips());
    }
}

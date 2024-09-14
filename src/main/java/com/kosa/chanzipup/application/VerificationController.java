package com.kosa.chanzipup.application;

import com.kosa.chanzipup.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;
    @PostMapping("/verify")
    public ApiResponse<Boolean> activateAccount(@RequestParam("code") String verificationCode) {
        return ApiResponse.ok(verificationService.activeAccount(verificationCode));
    }
}

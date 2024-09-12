package com.kosa.chanzipup.api.token.controller;

import com.kosa.chanzipup.api.ApiResponse;
import com.kosa.chanzipup.api.token.service.RefreshTokenService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refreshToken")
    public ApiResponse<String> issueAccessTokenUsingRefreshToken(@AuthenticationPrincipal UnifiedUserDetails userDetails,
                                                                 @CookieValue("refreshToken") String refreshToken) {
        String accessToken = refreshTokenService.reIssueAccessToken(refreshToken);
        return ApiResponse.ok(accessToken);
    }
}

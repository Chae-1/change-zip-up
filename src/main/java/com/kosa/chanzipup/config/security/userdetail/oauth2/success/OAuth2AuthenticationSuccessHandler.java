package com.kosa.chanzipup.config.security.userdetail.oauth2.success;


import com.kosa.chanzipup.api.token.service.RefreshTokenService;
import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final int REFRESH_EXPIRY_DURATION = 7 * 24 * 60 * 60;

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    // login 처리가 완료되면 jwt Token을 발급한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. 이미 회원 가입이 되어있는 경우를 상정하지 않았음.

        // 2. 이전에 회원 가입이 되어있지 않은 회원인 경우
        String email = authentication.getName();
        createAndSendAccessTokenInHeader(response, email);

        String refreshToken = createAndSaveRefreshToken(email);
        sendRefreshTokenUsingCookie(refreshToken, response);
    }

    private void createAndSendAccessTokenInHeader(HttpServletResponse response, String email) {
        String accessToken = jwtProvider.generateToken(email, TokenType.ACCESS);
        response.setHeader("Authorization", String.format("Bearer %s", accessToken));
    }


    // 1. refreshToken 발급하고 저장한다.
    private String createAndSaveRefreshToken(String email) {
        String refreshToken = jwtProvider.generateToken(email, TokenType.REFRESH);
        LocalDateTime expireDate = LocalDateTime.now().plusDays(7);
        refreshTokenService.saveRefreshTokenByAccountEmail(email, refreshToken, expireDate);
        return refreshToken;
    }

    // 2.
    private void sendRefreshTokenUsingCookie(String refreshToken, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 범위
        refreshTokenCookie.setMaxAge(REFRESH_EXPIRY_DURATION);
        response.addCookie(refreshTokenCookie);
    }
}

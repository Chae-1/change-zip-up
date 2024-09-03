package com.kosa.chanzipup.config.security.userdetail.oauth2.success;


import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    // login 처리가 완료되면 jwt Token을 발급한다.
    // 단, refreshToken은 httpOnly 쿠키로 전송
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String name = oauthToken.getName();

        // email을 기반으로 accessToken 발급 후, Authorization 헤더로 전송
        String accessToken = jwtProvider.generateToken(name, TokenType.ACCESS);
        response.setHeader("Authorization", String.format("Bearer %s", accessToken));

        String refreshToken = jwtProvider.generateToken(name, TokenType.REFRESH);

        // refreshToken 발급 이후, DB에 저장한 뒤,
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 범위
        refreshTokenCookie.setMaxAge(REFRESH_EXPIRY_DURATION);

        response.addCookie(refreshTokenCookie);
    }
}

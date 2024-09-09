package com.kosa.chanzipup.config.security.userdetail.oauth2.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.chanzipup.api.token.service.RefreshTokenService;
import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.member.MemberType;
import com.kosa.chanzipup.domain.account.token.RefreshToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final int REFRESH_EXPIRY_DURATION = 7 * 24 * 60 * 60;

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper mapper;

    // login 처리가 완료되면 jwt Token을 발급한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. 엑세스 토큰 발급은, 기존 리프레시 토큰을 가지고 있는 회원인지에 상관없이 발급한다.
        UnifiedUserDetails userDetails = (UnifiedUserDetails) authentication.getPrincipal();

        // todo: 회원 정보
        String email = userDetails.getName();
        String accessToken = createAccessToken(email);
        String registeredId = userDetails.getRegisteredId();
        String nickName = userDetails.nickName();

        // todo:
        String refreshToken = createAndSaveRefreshToken(email);
        sendRefreshTokenUsingCookie(refreshToken, response);

        // todo: 토큰 전달 방식을 수정하자.
        // oauth -> redirect를 해주고
        // formLogin 방식이면, response로 전달한다.
        afterLoginProcess(response, accessToken, registeredId, nickName);
    }


    private void afterLoginProcess(HttpServletResponse response,
                                   String accessToken,
                                   String registeredId, String nickName) throws IOException {

        // 1. 소셜 로그인이면 리다이렉트
        if (MemberType.isSocial(registeredId)) {
            response.sendRedirect(redirectURI(accessToken));
            return ;
        }

        // 2. 멤버 로그인이면, accessToken을 Authorization Header로 전달.
        String successDto = mapper.writeValueAsString(new LoginSuccessResponse(nickName));
        response.addHeader("Authorization", accessToken);
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(successDto);
    }
// Authorization

    private String redirectURI(String accessToken) {
        String fullAccessToken = String.format("Bearer %s", accessToken);
        return String.format("http://localhost:3000/oauth/redirect?authorization=%s", fullAccessToken);
    }

    private String createAccessToken(String email) {
        String accessToken = jwtProvider.generateToken(email, TokenType.ACCESS, LocalDateTime.now());
        return accessToken;
    }

    private String createAndSaveRefreshToken(String email) {
        RefreshToken refreshToken = refreshTokenService.saveRefreshTokenByAccountEmail(email);
        return refreshToken.getToken();
    }

    private void sendRefreshTokenUsingCookie(String refreshToken, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 범위
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(REFRESH_EXPIRY_DURATION);
        response.addCookie(refreshTokenCookie);
    }

    @Getter
    @AllArgsConstructor
    static class LoginSuccessResponse {
        private String nickName;
    }
}

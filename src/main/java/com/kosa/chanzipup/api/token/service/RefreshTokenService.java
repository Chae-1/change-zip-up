package com.kosa.chanzipup.api.token.service;

import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.account.token.RefreshToken;
import com.kosa.chanzipup.domain.account.token.RefreshTokenReIssueException;
import com.kosa.chanzipup.domain.account.token.RefreshTokenRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    // todo : 현재, 로그인을 단 한번이라도 수행한 기존 회원 고려하지 않은 채 refreshToken을 저장함.
    @Transactional
    public String saveRefreshTokenByAccountEmail(String email, LocalDateTime expiryDateTime) {
        // 1. email을 가지고 account을 포함한 refreshToken을 모두 조회한다.
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        // 2. 기존 회원 유저의 리프레시 토큰이 존재하면 새로운 토큰을 발급한다.

        // 3. 토큰이 발급되지 않았으면 발급요청
        String token = jwtProvider.generateToken(email, TokenType.REFRESH, LocalDateTime.now());
        RefreshToken accountRefreshToken = findAccount.getRefreshToken();

        if (accountRefreshToken == null) {
            RefreshToken savedRefreshToken = refreshTokenRepository.save(RefreshToken.of(token, expiryDateTime));
            findAccount.addRefreshToken(savedRefreshToken);
            return savedRefreshToken.getToken();
        }

        // 4. 새로운 토큰으로 갱신한다.
        accountRefreshToken.updateToken(token, expiryDateTime);

        return accountRefreshToken.getToken();
    }

    public String reIssueAccessToken(String refreshToken) {
        RefreshToken issuedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("재발급 요청 실패"));

        if (issuedRefreshToken.isExpired(LocalDateTime.now())) {
            throw new RefreshTokenReIssueException("리프레시 토큰을 통해 재발급을 실패 했음.");
        }

        String accessToken = jwtProvider.generateToken(issuedRefreshToken.getAccount().getEmail(), TokenType.ACCESS,
                LocalDateTime.now());
        return accessToken;
    }
}

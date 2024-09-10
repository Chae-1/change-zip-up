package com.kosa.chanzipup.api.token.service;

import com.kosa.chanzipup.config.security.jwt.JwtProvider;
import com.kosa.chanzipup.config.security.jwt.TokenType;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.account.token.RefreshToken;
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
    public RefreshToken saveRefreshTokenByAccountEmail(String email) {
        // 1. email을 가지고 account을 포함한 refreshToken을 모두 조회한다.
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        // 2. 기존 회원 유저의 리프레시 토큰이 존재하면 새로운 토큰을 발급한다.
        LocalDateTime issuedDate = LocalDateTime.now();
        String token = jwtProvider.generateToken(email, TokenType.REFRESH, issuedDate);

        // 3. 토큰이 발급되지 않았으면 발급요청
        RefreshToken accountRefreshToken = findAccount.getRefreshToken();
        if (accountRefreshToken == null) {
            RefreshToken savedRefreshToken = refreshTokenRepository.save(RefreshToken.of(token, issuedDate));
            findAccount.addRefreshToken(savedRefreshToken); // todo: 메서드 명 수정
            return savedRefreshToken;
        }

        // 4. 만약 기존에 토큰이 존재하지 않았다면 새로운 토큰을 발급한다.
        if (accountRefreshToken.isExpired(LocalDateTime.now())) {
            accountRefreshToken.updateToken(token, issuedDate);
        }
        return accountRefreshToken;
    }

}

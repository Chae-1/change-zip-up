package com.kosa.chanzipup.api.token.service;

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

    @Transactional
    public void saveRefreshTokenByAccountEmail(String email, String token, LocalDateTime expireDate) {
        // 1. email을 가지고 account을 포함한 refreshToken을 모두 조회한다.
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        // todo : 현재, 로그인을 단 한번이라도 수행한 기존 회원 고려하지 않은 채 refreshToken을 저장함.

        // 2. refershToken이 null이 아닌데,
        RefreshToken accountRefreshToken = findAccount.getRefreshToken();
        if (accountRefreshToken != null && accountRefreshToken.isExpired()) {
            accountRefreshToken.updateToken(token, expireDate);
            return;
        }

        // 3.

        // 4. token을 생성한다.
        RefreshToken refreshToken = RefreshToken.of(token, expireDate);
        findAccount.addRefreshToken(refreshToken); // todo: 메서드 명 수정
    }

}

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
        // 1. email을 가지고 account를 조회한다.
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        // 2. account를 기반으로 기존 토큰을 검색한다.
        // 2-1. 기존 토큰을 찾았고 유효하다면, 이를 반환한다.
        // 2-2. 유효하지 않은 토큰이면, 이를 폐기하고 새로운 토큰을 생성한 뒤 반환한다.


        // 2. token을 생성한다.
        RefreshToken refreshToken = RefreshToken.of(token, expireDate, findAccount);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
    }

}

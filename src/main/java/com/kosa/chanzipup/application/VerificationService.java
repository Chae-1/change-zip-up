package com.kosa.chanzipup.application;

import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.account.Verification;
import com.kosa.chanzipup.domain.account.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final AccountRepository accountRepository;

    // VerificationCode를 생성해서, Account에 대한 인증 코드를 생성해주는 역할.
    @Transactional
    public VerificationCode createVerificationCode(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        Verification verification = Verification.create(account);
        verificationRepository.save(verification);

        return VerificationCode.of(verification.getVerificationCode(), verification.getAccount().getEmail());
    }

    public boolean activeAccount(String verificationCode) {
        Verification verification = verificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new IllegalArgumentException());
        // 계정을 활성화 한다.
        verification.getAccount().active();
        return true;
    }
}

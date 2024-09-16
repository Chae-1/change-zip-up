package com.kosa.chanzipup.api.admin.service;

import com.kosa.chanzipup.api.admin.controller.request.AccountSearchCondition;
import com.kosa.chanzipup.domain.account.AdminAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AdminAccountRepository adminAccountRepository;

    public void getAllAccounts(Pageable pageable, AccountSearchCondition condition) {


    }
}

package com.kosa.chanzipup.api.admin.service;

import com.kosa.chanzipup.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;


    public void getAllAccounts() {

    }
}

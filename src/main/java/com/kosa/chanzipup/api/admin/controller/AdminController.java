package com.kosa.chanzipup.api.admin.controller;

import com.kosa.chanzipup.api.admin.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AccountService accountService;

    public void getAllAccounts() {
        accountService.getAllAccounts();
    }
}

package com.kosa.chanzipup.api.portfolio.controller;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;
import com.kosa.chanzipup.api.portfolio.service.PortfolioService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<?> addPortfolio(
            @Valid @RequestBody PortfolioRegisterRequest portfolioRequest,
            @AuthenticationPrincipal UnifiedUserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email: " + email));

        try {
            // 포트폴리오 저장 서비스 호출
            PortfolioRegisterResponse savedPortfolioResponse = portfolioService.registerPortfolio(portfolioRequest, account);
            return ResponseEntity.ok(savedPortfolioResponse);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to save portfolio");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

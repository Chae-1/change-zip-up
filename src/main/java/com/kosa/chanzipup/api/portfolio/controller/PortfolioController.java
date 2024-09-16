package com.kosa.chanzipup.api.portfolio.controller;

import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioDetailResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioListResponse;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;
import com.kosa.chanzipup.api.portfolio.service.PortfolioService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.portfolio.PortfolioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
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

    // 시공사례 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<PortfolioListResponse>> listPortfolios() {
        List<PortfolioListResponse> portfolios = portfolioService.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    // 시공사례 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDetailResponse> getPortfolioById(@PathVariable long id) {
        PortfolioDetailResponse portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);

    }

}

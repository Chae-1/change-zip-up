package com.kosa.chanzipup.api.portfolio.controller;

import com.kosa.chanzipup.api.company.service.CompanyService;
import com.kosa.chanzipup.api.portfolio.controller.request.PortfolioRegisterRequest;
import com.kosa.chanzipup.api.portfolio.controller.response.PortfolioRegisterResponse;
import com.kosa.chanzipup.api.portfolio.service.PortfolioService;
import com.kosa.chanzipup.domain.account.Account;
import com.kosa.chanzipup.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createPortfolio(
            @RequestPart("portfolioRequest") PortfolioRegisterRequest portfolioRequest  // 포트폴리오 정보
    ) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();  // 인증된 사용자의 Account 객체를 직접 가져오기

        try {
            Long accountId = account.getId(); // 조회된 Account 엔티티에서 accountId 가져오기

            // 포트폴리오 저장 서비스 호출
            PortfolioRegisterResponse savedPortfolioResponse = portfolioService.registerPortfolio(portfolioRequest, accountId);
            return ResponseEntity.ok(savedPortfolioResponse); // 성공 시 포트폴리오 ID 반환
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to save portfolio");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 잘못된 요청 처리
        }
    }
}

package com.kosa.chanzipup.api.admin.controller;

import com.kosa.chanzipup.api.admin.controller.request.AccountSearchCondition;
import com.kosa.chanzipup.api.admin.controller.request.notice.NoticeCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.membership.MembershipCompanyResponse;
import com.kosa.chanzipup.api.admin.controller.response.notice.NoticeListResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.portfolio.PortfolioListResponse;
import com.kosa.chanzipup.api.admin.service.AccountService;
import com.kosa.chanzipup.api.admin.service.membership.AdminMembershipService;
import com.kosa.chanzipup.api.admin.service.notice.NoticeService;
import com.kosa.chanzipup.api.admin.service.portfolio.PortfolioServiceForAdmin;
import com.kosa.chanzipup.application.Page;
import java.util.List;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AccountService accountService;

    private final AdminMembershipService adminMembershipService;

    private final NoticeService noticeService;

    private final PortfolioServiceForAdmin portfolioServiceForAdmin;

    @GetMapping("/accounts")
    public void getAllAccounts(@PageableDefault Pageable pageable, AccountSearchCondition condition) {
        log.info("pageable = {}", pageable);
        log.info("accountSearchCondition = {}", condition);
        accountService.getAllAccounts(pageable, condition);
    }


    @GetMapping("/memberships")
    public ResponseEntity<Page<List<MembershipCompanyResponse>>> getAllMembershipAccounts(
            @PageableDefault Pageable pageable
    ) {
        log.info("pageable = {}", pageable);
        return ResponseEntity.ok(
                adminMembershipService.getAllMembershipCompanies(pageable)
        );
    }

    @PostMapping("/notice/create")
    public ResponseEntity<Void> createNotice(@AuthenticationPrincipal UnifiedUserDetails userDetails
            , @RequestBody NoticeCreateRequestDto noticeCreateRequestDto) {
        String email = userDetails.getUsername();
        noticeService.createNotice(noticeCreateRequestDto, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notice/list")
    public ResponseEntity<List<NoticeListResponseDto>> listNotice() {
        List<NoticeListResponseDto> notices = noticeService.getNoticeList();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/portfolios")
    public ResponseEntity<Page<List<PortfolioListResponse>>> getAllPortfolios(@PageableDefault Pageable pageable) {
        log.info("pageable = {}", pageable);
        Page<List<PortfolioListResponse>> portfolios = portfolioServiceForAdmin.getAllPortfolios(pageable);
        return ResponseEntity.ok(portfolios);
    }
}

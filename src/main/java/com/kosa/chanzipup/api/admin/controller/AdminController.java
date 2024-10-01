package com.kosa.chanzipup.api.admin.controller;

import com.kosa.chanzipup.api.admin.controller.request.AccountSearchCondition;
import com.kosa.chanzipup.api.admin.controller.request.notice.NoticeCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.company.AdminCompanyResponse;
import com.kosa.chanzipup.api.admin.controller.response.member.AdminMemberResponse;
import com.kosa.chanzipup.api.admin.controller.response.membership.MembershipCompanyResponse;
import com.kosa.chanzipup.api.admin.controller.response.notice.NoticeListResponseDto;
import com.kosa.chanzipup.api.admin.service.AccountService;
import com.kosa.chanzipup.api.admin.service.membership.AdminMembershipService;
import com.kosa.chanzipup.api.admin.service.notice.NoticeService;
import com.kosa.chanzipup.application.Page;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/members")
    public ResponseEntity<List<AdminMemberResponse>> getAllMembers() {
        List<AdminMemberResponse> members = accountService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<AdminMemberResponse> getMember(@PathVariable Long id) {
        Optional<AdminMemberResponse> member = accountService.getMemberDetail(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/companies")
    public ResponseEntity<List<AdminCompanyResponse>> getAllCompanies() {
        List<AdminCompanyResponse> companies = accountService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    // 특정 회사 상세 조회
    @GetMapping("/company/{id}")
    public ResponseEntity<AdminCompanyResponse> getCompanyDetail(@PathVariable Long id) {
        Optional<AdminCompanyResponse> company = accountService.getCompanyDetail(id);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

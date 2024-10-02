package com.kosa.chanzipup.api.admin.controller;

import com.kosa.chanzipup.api.admin.controller.request.AccountSearchCondition;
import com.kosa.chanzipup.api.admin.controller.request.faq.FAQCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.faq.FAQUpdateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.notice.AdminNoticeCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.notice.AdminNoticeUpdateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.company.AdminCompanyResponse;
import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQListResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.member.AdminMemberResponse;
import com.kosa.chanzipup.api.admin.controller.response.membership.MembershipCompanyResponse;
import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeListResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.portfolio.PortfolioListResponse;
import com.kosa.chanzipup.api.admin.service.AccountService;
import com.kosa.chanzipup.api.admin.service.faq.AdminFAQService;
import com.kosa.chanzipup.api.admin.service.membership.AdminMembershipService;
import com.kosa.chanzipup.api.admin.service.notice.AdminNoticeService;
import com.kosa.chanzipup.api.admin.service.portfolio.PortfolioServiceForAdmin;
import com.kosa.chanzipup.application.Page;
import java.util.List;
import java.util.Optional;

import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.membership.MembershipType;
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

    private final AdminNoticeService adminNoticeService;

    private final AdminFAQService adminFaqService;

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
            , @RequestBody AdminNoticeCreateRequestDto adminNoticeCreateRequestDto) {
        String email = userDetails.getUsername();
        adminNoticeService.createNotice(adminNoticeCreateRequestDto, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notice/list")
    public ResponseEntity<List<AdminNoticeListResponseDto>> listNotice() {
        List<AdminNoticeListResponseDto> notices = adminNoticeService.getNoticeList();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/notice/{id}")
    public ResponseEntity<AdminNoticeDetailResponseDto> getNoticeById(@PathVariable Long id) {
        AdminNoticeDetailResponseDto adminNoticeDetailResponseDto = adminNoticeService.getNoticeById(id);
        return ResponseEntity.ok(adminNoticeDetailResponseDto);
    }

    @PatchMapping("/notice/{id}")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long id,
            @AuthenticationPrincipal UnifiedUserDetails userDetails,
            @RequestBody AdminNoticeUpdateRequestDto adminNoticeUpdateRequestDto) {
        String email = userDetails.getUsername();
        adminNoticeService.patchNotice(id, adminNoticeUpdateRequestDto, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/notice/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        adminNoticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/faq/create")
    public ResponseEntity<Void> createFAQ(@AuthenticationPrincipal UnifiedUserDetails userDetails
            , @RequestBody FAQCreateRequestDto faqCreateRequestDto) {
        String email = userDetails.getUsername();
        adminFaqService.createFAQ(faqCreateRequestDto, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/faq/list")
    public ResponseEntity<List<AdminFAQListResponseDto>> listFAQ() {
        List<AdminFAQListResponseDto> faqs = adminFaqService.getFAQList();
        return ResponseEntity.ok(faqs);
    }

    @GetMapping("/faq/{id}")
    public ResponseEntity<AdminFAQDetailResponseDto> getFAQById(@PathVariable Long id) {
        AdminFAQDetailResponseDto adminFaqDetailResponseDto = adminFaqService.getFAQById(id);
        return ResponseEntity.ok(adminFaqDetailResponseDto);
    }

    @PatchMapping("/faq/{id}")
    public ResponseEntity<Void> updateFAQ(
            @PathVariable Long id,
            @AuthenticationPrincipal UnifiedUserDetails userDetails,
            @RequestBody FAQUpdateRequestDto faqUpdateRequestDto) {
        String email = userDetails.getUsername();
        adminFaqService.patchFAQ(id, faqUpdateRequestDto, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/faq/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        adminFaqService.deleteFAQ(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/portfolios")
    public ResponseEntity<Page<List<PortfolioListResponse>>> getAllPortfolios(@PageableDefault Pageable pageable) {
        log.info("pageable = {}", pageable);
        Page<List<PortfolioListResponse>> portfolios = portfolioServiceForAdmin.getAllPortfolios(pageable);
        return ResponseEntity.ok(portfolios);
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

    @GetMapping("/company/{id}")
    public ResponseEntity<AdminCompanyResponse> getCompanyDetail(@PathVariable Long id) {
        Optional<AdminCompanyResponse> company = accountService.getCompanyDetail(id);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/membership/list")
    public ResponseEntity<List<MembershipType>> getMemberships() {
        List<MembershipType> membershipTypes = adminMembershipService.getAllMembershipTypes();
        return ResponseEntity.ok(membershipTypes);
    }

    // 특정 멤버십 타입 조회
    @GetMapping("/membership/{id}")
    public ResponseEntity<MembershipType> getMembershipTypeById(@PathVariable Long id) {
        Optional<MembershipType> membershipType = adminMembershipService.getMembershipTypeById(id);
        return membershipType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 멤버십 타입 생성
    @PostMapping("/membership")
    public ResponseEntity<MembershipType> createMembershipType(@RequestBody MembershipType membershipType) {
        MembershipType createdMembershipType = adminMembershipService.createMembershipType(membershipType);
        return ResponseEntity.ok(createdMembershipType);
    }

    // 멤버십 타입 수정
    @PatchMapping("/membership/{id}")
    public ResponseEntity<MembershipType> updateMembershipType(
            @PathVariable Long id,
            @RequestBody MembershipType updatedMembershipType
    ) {
        Optional<MembershipType> membershipType = adminMembershipService.updateMembershipType(id, updatedMembershipType);
        return membershipType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 멤버십 타입 삭제
    @DeleteMapping("/membership/{id}")
    public ResponseEntity<Void> deleteMembershipType(@PathVariable Long id) {
        adminMembershipService.deleteMembershipType(id);
        return ResponseEntity.ok().build();
    }

}

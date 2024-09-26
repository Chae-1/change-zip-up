package com.kosa.chanzipup.api.company.controller;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.request.CompanyUpdateRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.api.company.service.CompanyQueryService;
import com.kosa.chanzipup.api.company.service.CompanyService;
import com.kosa.chanzipup.api.review.controller.response.CompanyMyPage;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.application.PageInfo;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import com.kosa.chanzipup.domain.membership.MembershipName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyQueryService companyQueryService;

    // 업체 등록
    @PostMapping
    public ResponseEntity<CompanyRegisterResponse> addCompany(@Valid CompanyRegisterRequest registerRequest) {
        CompanyRegisterResponse savedCompany = companyService.registerCompany(registerRequest);
        return ResponseEntity.ok(savedCompany);
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isDuplicated = companyService.isEmailDuplicated(email);
        return ResponseEntity.ok(isDuplicated);
    }

    // 업체 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Map<MembershipName, Page<List<CompanyListResponse>>>> getAllCompany(
            @ModelAttribute PageInfo pageInfo,
            @ModelAttribute CompanySear chCondition searchCondition
    ) {
        Map<MembershipName, Page<List<CompanyListResponse>>> map = companyQueryService.getAllCompanies(pageInfo.getPage(),
                pageInfo.getSize(),
                searchCondition);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/categorylist")
    public ResponseEntity<Page<List<CompanyListResponse>>> getCompanyListWithPage(@ModelAttribute PageInfo pageInfo,
                                                                                  @ModelAttribute CompanySearchCondition searchCondition,
                                                                                  @RequestParam("name") MembershipName name) {

        return ResponseEntity.ok(companyQueryService.getSpecifiedMembershipCompaniesWithPage(pageInfo.getPage(), pageInfo.getSize(), name, searchCondition));
    }


    // 업체 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyById(@PathVariable Long id) {
        CompanyDetailResponse companyResponse = companyQueryService.getCompanyDetailResponse(id);
        return ResponseEntity.ok(companyResponse);
    }


    @GetMapping("/mypage")
    public ResponseEntity<CompanyMyPage> getMyPage(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(companyService.getCompanyMyPage(email));
    }

    @PatchMapping("/mypage")
    public ResponseEntity<Boolean> afterUpdateCompany(@AuthenticationPrincipal UnifiedUserDetails userDetails,
                                                            @RequestBody CompanyUpdateRequest request) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(companyService.updateCompany(email, request));
    }
}

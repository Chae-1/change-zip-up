package com.kosa.chanzipup.api.company.controller;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.api.company.service.CompanyQueryService;
import com.kosa.chanzipup.api.company.service.CompanyService;
import com.kosa.chanzipup.domain.membership.MembershipName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CompanyRegisterResponse> addCompany(@Valid @RequestBody CompanyRegisterRequest registerRequest) {
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
    public ResponseEntity<List<CompanyListResponse>> getAllCompany(@ModelAttribute CompanySearchCondition searchCondition) {
        List<CompanyListResponse> companies = companyQueryService.getAllCompanies(searchCondition);
        return ResponseEntity.ok(companies);
    }

    // 업체 리스트 조회
    @GetMapping("/list2")
    public ResponseEntity<Map<MembershipName, List<CompanyListResponse>>> getAllCompany2(@ModelAttribute CompanySearchCondition searchCondition) {
        Map<MembershipName, List<CompanyListResponse>> map = companyQueryService.getAllCompanies2(searchCondition);
        return ResponseEntity.ok(map);
    }


    // 업체 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyById(@PathVariable Long id) {
        CompanyDetailResponse companyResponse = companyQueryService.getCompanyDetailResponse(id);
        return ResponseEntity.ok(companyResponse);
    }

}

package com.kosa.chanzipup.api.company.controller;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.api.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    // 업체 등록
    @PostMapping
    public ResponseEntity<CompanyRegisterResponse> addCompany(@RequestBody CompanyRegisterRequest registerRequest) {
        System.out.println("Construction Service IDs: " + registerRequest.getConstructionService());

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
    public ResponseEntity<List<CompanyListResponse>> getAllCompany() {
        List<CompanyListResponse> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    // 업체 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyById(@PathVariable Long id) {
        CompanyDetailResponse companyResponse = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyResponse);
    }

}
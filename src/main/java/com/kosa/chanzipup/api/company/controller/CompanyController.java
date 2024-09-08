package com.kosa.chanzipup.api.company.controller;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.api.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

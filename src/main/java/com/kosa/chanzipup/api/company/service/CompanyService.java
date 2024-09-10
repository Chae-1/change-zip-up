package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.companyConstructionType.CompanyConstructionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public CompanyRegisterResponse registerCompany(CompanyRegisterRequest request) {

        // 이메일 중복 확인
        if (isEmailDuplicated(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Company company = Company.ofNewCompany(request.getEmail(), request.getCompanyName(), request.getPassword(),
                request.getPhoneNumber(), request.getOwner(), request.getCompanyNumber(), request.getPublishDate(),
                request.getAddress(), request.getCompanyDesc());

        // 선택된 시공 타입 저장
        List<Long> selectedTypeIds = request.getConstructionService();
        selectedTypeIds.forEach(typeId -> {
            ConstructionType constructionType = constructionTypeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type ID: " + typeId));
            CompanyConstructionType companyConstructionType = new CompanyConstructionType(constructionType, company);
            company.addConstructionType(companyConstructionType);  // Company 객체에 추가
        });

        companyRepository.save(company);
        return CompanyRegisterResponse.of(request.getEmail(), request.getCompanyName());
    }

    // 이메일 중복 확인
    public boolean isEmailDuplicated(String email) {
        return accountRepository.existsByEmail(email);
    }

    // 업체 리스트 조회
    public List<CompanyListResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyListResponse> responses = new ArrayList<>();

        for (Company company : companies) {
            List<String> services = new ArrayList<>();
            for (CompanyConstructionType constructionType : company.getConstructionTypes()) {
                services.add(constructionType.getConstructionType().getName());
            }
            responses.add(new CompanyListResponse(
                    company.getId(),
                    company.getCompanyName(),
                    company.getCompanyDesc(),
                    company.getCompanyLogoUrl(),
                    company.getRating(),
                    services
            ));
        }
        return responses;

//        List<String> services = new ArrayList<>();
//
//        return companyRepository.findAll().stream()
//                .map(company -> new CompanyListResponse(
//                        company.getId(),
//                        company.getCompanyName(),
//                        company.getCompanyDesc(),
//                        company.getCompanyLogoUrl(),
//                        company.getRating(),
//                        company.getConstructionTypes().stream()
//                                .map(constructionType -> constructionType.getConstructionType().getName())
//                                .collect(Collectors.toList())
//                ))
//                .collect(Collectors.toList());

    }

    // 업체 상세 조회
    public CompanyDetailResponse getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        List<String> services = new ArrayList<>();
        for (CompanyConstructionType constructionType : company.getConstructionTypes()) {
            services.add(constructionType.getConstructionType().getName());
        }

//        List<String> services = company.getConstructionTypes().stream()
//                .map(constructionType -> constructionType.getConstructionType().getName())
//                .collect(Collectors.toList());

        return new CompanyDetailResponse(
                company.getId(),
                company.getCompanyName(),
                company.getCompanyNumber(),
                company.getOwner(),
                company.getAddress(),
                company.getCompanyLogoUrl(),
                company.getPhoneNumber(),
                company.getCompanyDesc(),
                company.getPublishDate(),
                company.getRating(),
                services
        );
    }
}

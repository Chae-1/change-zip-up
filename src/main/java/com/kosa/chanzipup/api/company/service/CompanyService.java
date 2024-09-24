package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.application.PathMatchService;
import com.kosa.chanzipup.application.images.ImageService;
import com.kosa.chanzipup.domain.account.company.CompanyConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.account.AccountRepository;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ImageService imageService;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;
    private final PathMatchService pathMatchService;

    @Transactional
    public CompanyRegisterResponse registerCompany(CompanyRegisterRequest request) {

        // 이메일 중복 확인
        if (isEmailDuplicated(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String uploadEndPoint = imageService.store("company", request.getLogoFile());

        Company company = Company.ofNewCompany(request.getEmail(), request.getCompanyName(), encoder.encode(request.getPassword()),
                request.getPhoneNumber(), request.getOwner(), request.getCompanyNumber(), request.getPublishDate(),
                request.getAddress(), request.getCompanyDesc(), pathMatchService.match(uploadEndPoint));

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

    // 업체 상세 조회
//    public CompanyDetailResponse getCompanyById(Long companyId) {
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
//
//        List<String> services = new ArrayList<>();
//        for (CompanyConstructionType constructionType : company.getConstructionTypes()) {
//            services.add(constructionType.getConstructionType().getName());
//        }
//
//        return new CompanyDetailResponse(
//                company.getId(),
//                company.getCompanyName(),
//                company.getCompanyNumber(),
//                company.getOwner(),
//                company.getAddress(),
//                company.getCompanyLogoUrl(),
//                company.getPhoneNumber(),
//                company.getCompanyDesc(),
//                company.getPublishDate(),
//                company.getRating(),
//                services,
//                null,
//                null
//        );
//    }

    public Long findCompanyIdByEmail(String email) {
        Company company = (Company) companyRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No company found with email: " + email));
        return company.getId();
    }
}

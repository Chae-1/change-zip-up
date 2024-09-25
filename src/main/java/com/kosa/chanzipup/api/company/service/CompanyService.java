package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.request.CompanyUpdateRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.api.review.controller.response.CompanyMyPage;
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
import java.util.stream.Stream;

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
        List<ConstructionType> constructionTypes = constructionTypeRepository
                .findByIdIn(request.getConstructionService());
        company.addConstructionTypes(constructionTypes);

        companyRepository.save(company);
        return CompanyRegisterResponse.of(request.getEmail(), request.getCompanyName());
    }

    // 이메일 중복 확인
    public boolean isEmailDuplicated(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Long findCompanyIdByEmail(String email) {
        Company company = (Company) companyRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No company found with email: " + email));
        return company.getId();
    }

    public CompanyMyPage getCompanyMyPage(String email) {
        Company company = companyRepository.findByEmailWithAll(email)
                .orElseThrow(() -> new IllegalArgumentException("No company found with email: " + email));
        List<ConstructionType> constructionTypes = constructionTypeRepository.findAll();

        return new CompanyMyPage(company, constructionTypes);

    }

    @Transactional
    public boolean updateCompany(String email, CompanyUpdateRequest request) {
        Company company = companyRepository.findByEmailWithAll(email)
                .orElseThrow(() -> new IllegalArgumentException("No company found with email: " + email));
        // 1. 업데이트 할, ConstructionType
        List<ConstructionType> findConstructionTypes = constructionTypeRepository
                .findByIdIn(getUpdateServices(request, company));

        company.addConstructionTypes(findConstructionTypes);
        return true;
    }

    private List<Long> getUpdateServices(CompanyUpdateRequest request, Company company) {
        List<Long> requestUpdateServices = request.getUpdateServices(); // 1, 2
        List<Long> currentCompanyServices = company.getConstructionTypes() // 1
                .stream()
                .map(type -> type.getConstructionType().getId())
                .toList();

        requestUpdateServices.removeAll(currentCompanyServices);
        return requestUpdateServices;
    }
}

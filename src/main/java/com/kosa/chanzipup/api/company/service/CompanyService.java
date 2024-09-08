package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import com.kosa.chanzipup.domain.ConstructionType.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.companyConstructionType.CompanyConstructionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ConstructionTypeRepository constructionTypeRepository;

    @Transactional
    public CompanyRegisterResponse registerCompany(CompanyRegisterRequest request) {

        // 이메일 중복 확인
        if(!isEmailDuplicated(request.getEmail())) {
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
            CompanyConstructionType companyConstructionType = new CompanyConstructionType(constructionType, company); // 변경
            company.addConstructionType(companyConstructionType);  // Company 객체에 추가
        });

        companyRepository.save(company);

        return CompanyRegisterResponse.of(request.getEmail(), request.getCompanyName());
    }

    // 이메일 중복 확인
    public boolean isEmailDuplicated(String email) {
        return companyRepository.existsByEmail(email);
    }
}

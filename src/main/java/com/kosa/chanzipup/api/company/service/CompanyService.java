package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanyRegisterRequest;
import com.kosa.chanzipup.api.company.controller.response.CompanyRegisterResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyRegisterResponse registerCompany(CompanyRegisterRequest request) {
        Company company = Company.ofNewCompany(request.getEmail(), request.getCompanyName(), request.getPassword(),
                request.getPhoneNumber(), request.getOwner(), request.getCompanyNumber(), request.getPublishDate());
        companyRepository.save(company);
        return CompanyRegisterResponse.of(company.getEmail(), request.getCompanyName());
    }
}

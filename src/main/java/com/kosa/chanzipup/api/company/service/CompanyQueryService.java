package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.company.QCompanyConstructionType.companyConstructionType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyQueryService {

    private final JPAQueryFactory factory;


    public List<CompanyListResponse> getAllCompanies(CompanySearchCondition searchCondition) {
        log.info("지역 : {}", searchCondition.getCity());
        log.info("구 : {}", searchCondition.getDistrict());
        log.info("서비스 리스트 : {}", searchCondition.getServices());
        List<Company> companyList = factory.select(company)
                .from(company)
                .leftJoin(company.constructionTypes, companyConstructionType)
                .leftJoin(companyConstructionType.constructionType, constructionType)
                .where(constructionTypeIn(searchCondition.getServices()), getContains(searchCondition.getCity()))
                .fetch();

        return companyList.stream()
                .map(company -> new CompanyListResponse(company))
                .toList();
    }

    private BooleanExpression getContains(String city) {

        if (city == null || city.isBlank()) {
            return null;
        }
        return company.address.contains(city);
    }

    private BooleanExpression constructionTypeIn(List<Long> constructionIds) {

        if (constructionIds == null || constructionIds.isEmpty()) {
            return null;
        }

        return constructionType.id.in(constructionIds);
    }
}

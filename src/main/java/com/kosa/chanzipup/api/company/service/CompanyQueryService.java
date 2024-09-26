package com.kosa.chanzipup.api.company.service;

import com.kosa.chanzipup.api.company.controller.request.CompanySearchCondition;
import com.kosa.chanzipup.api.company.controller.response.CompanyDetailResponse;
import com.kosa.chanzipup.api.company.controller.response.CompanyListResponse;
import com.kosa.chanzipup.application.Page;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyConstructionType;
import com.kosa.chanzipup.domain.account.company.CompanyQueryRepository;
import com.kosa.chanzipup.domain.membership.*;
import com.kosa.chanzipup.domain.portfolio.Portfolio;
import com.kosa.chanzipup.domain.review.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.membership.QMembership.membership;
import static com.kosa.chanzipup.domain.membership.QMembershipType.membershipType;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CompanyQueryService {

    private final CompanyQueryRepository queryRepository;

    private final JPAQueryFactory factory;

    // 현행되고 있는 컴퍼니 조회 방식.
    public Map<MembershipName, Page<List<CompanyListResponse>>> getAllCompanies(
            int page, int size,
            CompanySearchCondition searchCondition
    ) {
        // 1. 지역을 기반으로 회사를 조회한다.
        // -> 회원의 멤버십과 함께 조회한다.
        List<Company> findCompaniesWithMemberships = queryRepository.findByCityAndDistrictWithMemberships(
                searchCondition.getCity(), searchCondition.getDistrict());

        // 2. 조회한 회사들의 시공 타입을 조회한다.
        List<Long> companyIds = getCompanyIds(findCompaniesWithMemberships);

        // 2-1. 회사 Id를 key, Construction List를 value로 하는 Map을 통해서
        // ! -> 실제 컴퍼니 타입이 조회되지 않을 수도 있음
        // 그래서 추가적으로 실제하는 회사들을 기반으로 새로운 Map을 생성해야함.
        Map<Long, List<CompanyConstructionType>> companyConstructionTypeMap = companyConstructionMap(
                companyIds);

        // 2-2. 검색 조건을 통해 전달된 typeId를 모두 포함하는 회원 ID를 확인한다.
        List<Long> services = searchCondition.getServices();
        List<Long> filteredCompany = getCompanyIdsContainAllConstructionType(services,
                companyIds,
                companyConstructionTypeMap);

        Map<MembershipName, List<CompanyListResponse>> membershipCompany = mappingMembershipName(
                findCompaniesWithMemberships,
                companyConstructionTypeMap, filteredCompany);

        return membershipCompany.entrySet()
                .stream()
                .collect(toMap(
                        entry -> entry.getKey(),
                        entry -> Page.of(entry.getValue(), size, page)
                ));

    }

    private Map<Long, List<CompanyConstructionType>> companyConstructionMap(List<Long> companyIds) {
        Map<Long, List<CompanyConstructionType>> companyConstructionType = queryRepository
                .findByConstructionTypesInCompanyIds(companyIds)
                .stream()
                .collect(groupingBy(type -> type.getCompany().getId(), toList()));

        // 실제하는 컴퍼니에 옮겨야함
        return companyIds.stream()
                .collect(toMap(
                        id -> id,
                        id -> companyConstructionType.getOrDefault(id, new ArrayList<>())
                ));
    }

    private Map<MembershipName, List<CompanyListResponse>> mappingMembershipName(
            List<Company> findCompaniesWithMemberships,
            Map<Long, List<CompanyConstructionType>> companyConstructionTypeMap,
            List<Long> filteredCompany) {

        Map<MembershipName, List<CompanyListResponse>> map = new HashMap<>();
        map.put(MembershipName.NO, new ArrayList<>());
        map.put(MembershipName.BASIC, new ArrayList<>());
        map.put(MembershipName.PREMIUM, new ArrayList<>());

        findCompaniesWithMemberships
                .stream()
                .filter(company -> filteredCompany.contains(company.getId()))
                .forEach(company -> {
                    Membership activeMembership = company.getActiveMembership();
                    List<CompanyConstructionType> companyConstructionTypes = companyConstructionTypeMap.get(
                            company.getId());
                    if (activeMembership == null) {
                        map.get(MembershipName.NO).add(new CompanyListResponse(company, companyConstructionTypes));
                        return ;
                    }
                    map.get(activeMembership.getMembershipName()).add(new CompanyListResponse(company, companyConstructionTypes));
                });

        return map;
    }

    private List<Long> getCompanyIdsContainAllConstructionType(List<Long> searchConstructionIds,
                                                               List<Long> companyIds, Map<Long, List<CompanyConstructionType>> companyConstructionTypeMap) {

        return companyIds.stream()
                .filter(companyId -> {
                    if (searchConstructionIds == null) {
                        return true;
                    }

                    List<Long> companyTypeIds = companyConstructionTypeMap.get(companyId)
                            .stream()
                            .map(type -> type.getConstructionType().getId())
                            .collect(toList());

                    return companyTypeIds.containsAll(searchConstructionIds);
                })
                .toList();



    }

    private List<Long> getCompanyIds(List<Company> findCompaniesWithMemberships) {
        return findCompaniesWithMemberships
                .stream()
                .map(Company::getId).toList();
    }

    private List<Membership> getActiveMembershipsWithCompany(MembershipName membershipName) {
        return factory.selectFrom(membership)
                .leftJoin(membership.company, company).fetchJoin()
                .leftJoin(membership.membershipType, membershipType).fetchJoin()
                .where(membershipNameEq(membershipName), membership.endDateTime.after(LocalDateTime.now()))
                .orderBy(membership.startDateTime.desc())
                .fetch();
    }

    private BooleanExpression membershipNameEq(MembershipName membershipName) {
        if (membershipName == null) {
            return null;
        }

        return membershipType.name.eq(membershipName);
    }


    public Page<List<CompanyListResponse>> getSpecifiedMembershipCompaniesWithPage(int page, int size,
                                                                                   MembershipName membershipName,
                                                                                   CompanySearchCondition searchCondition) {

        Map<MembershipName, Page<List<CompanyListResponse>>> membershipNameListMap = getAllCompanies(page, size,
                searchCondition);
        return membershipNameListMap.get(membershipName);

    }


    public CompanyDetailResponse getCompanyDetailResponse(Long companyId) {

        // 1. 고객들이 작성한 회사의 시공 후기
        List<Review> companyReviewList = queryRepository.findAllCompanyReviews(companyId);

        // 2. 업체 수행한 시공 사례
        List<Portfolio> companyPortfolioList = queryRepository.findAllCompanyPortfolios(companyId);

        // 3. 회사 정보
        Company findCompany = queryRepository.findCompanyByIdWithConstructionTypes(companyId);

        // 4.
        return new CompanyDetailResponse(
                companyReviewList,
                companyPortfolioList,
                findCompany
        );

    }
}

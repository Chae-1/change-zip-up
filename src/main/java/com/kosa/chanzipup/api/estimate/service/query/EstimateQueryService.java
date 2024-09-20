package com.kosa.chanzipup.api.estimate.service.query;

import com.kosa.chanzipup.api.estimate.controller.response.EstimateConstructionResponse;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestResponse;
import com.kosa.chanzipup.domain.estimate.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimate.estimate;
import static com.kosa.chanzipup.domain.estimate.QEstimateConstructionType.estimateConstructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimateRequest.estimateRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstimateQueryService {
    private final JPAQueryFactory factory;

    public List<EstimateRequestResponse> getEstimateRequestResponsesOnWaiting(String companyEmail) {

        List<Estimate> fetch = factory.select(estimate)
                .from(estimate) // 1
                .leftJoin(estimate.estimateRequest, estimateRequest).fetchJoin() // 1
                .leftJoin(estimate.company, company).fetchJoin() // 1
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin() // n - 1
                .where(estimateRequest.status.eq(EstimateRequestStatus.WAITING))
                .fetch();

        return fetch.stream()
                .map(estimate -> new EstimateRequestResponse(estimate, companyEmail))
                .toList();
    }


    public List<EstimateRequestResponse> getAllReceivedEstimate(String companyEmail) {
        List<Estimate> fetch = factory.select(estimate)
                .from(estimate) // 1
                .leftJoin(estimate.estimateRequest, estimateRequest).fetchJoin() // 1
                .leftJoin(estimate.company, company).fetchJoin() // 1
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin() // n - 1
                .where(estimateRequest.status.eq(EstimateRequestStatus.WAITING),
                        estimate.estimateStatus.eq(EstimateStatus.RECEIVED),
                        company.email.eq(companyEmail))
                .fetch();

        return fetch.stream()
                .map(estimate -> new EstimateRequestResponse(estimate, companyEmail))
                .toList();

    }

    public List<EstimateConstructionResponse> getEstimatePriceDetail(Long estimateRequestId) {

        Optional<EstimateRequest> findRequest = Optional.of(
                factory
                        .select(estimateRequest)
                        .from(estimateRequest)
                        .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin()
                        .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin()
                        .where(estimateRequest.id.eq(estimateRequestId))
                        .fetchOne()
        );

        List<EstimateConstructionResponse> estimateConstructionResponses = findRequest
                .map(request -> request.getConstructionTypes()
                        .stream()
                        .map(type -> new EstimateConstructionResponse(type.getId(), type.getTypeName()))
                        .toList())
                .orElse(null);

        return estimateConstructionResponses;
    }

    public List<EstimateRequestResponse> getAllEstimateRequestByUser(String userEmail) {

        List<EstimateRequest> requests = factory.select(estimateRequest)
                .from(estimateRequest)
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin()
                .where(member.email.eq(userEmail),
                        estimateRequest.status.eq(EstimateRequestStatus.WAITING))
                .fetch();// n - 1

        return requests.stream()
                .map(estimateRequest -> new EstimateRequestResponse(estimateRequest))
                .toList();
    }

    public void findAllEstimateOnEstimateRequest(Long requestId, String username) {
        List<Estimate> fetch = factory.select(estimate)
                .from(estimate) // 1
                .leftJoin(estimate.estimateRequest, estimateRequest).fetchJoin() // 1
                .leftJoin(estimate.company, company).fetchJoin() // 1
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin() // n - 1
                .where(estimateRequest.status.eq(EstimateRequestStatus.WAITING))
                .fetch();

    }
}

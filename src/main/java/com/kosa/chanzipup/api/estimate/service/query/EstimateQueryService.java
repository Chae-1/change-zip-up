package com.kosa.chanzipup.api.estimate.service.query;

import com.kosa.chanzipup.api.estimate.controller.response.*;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.estimate.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.account.member.QMember.member;
import static com.kosa.chanzipup.domain.buildingtype.QBuildingType.buildingType;
import static com.kosa.chanzipup.domain.constructiontype.QConstructionType.constructionType;
import static com.kosa.chanzipup.domain.estimate.EstimateRequestStatus.ONGOING;
import static com.kosa.chanzipup.domain.estimate.EstimateStatus.ACCEPTED;
import static com.kosa.chanzipup.domain.estimate.QEstimate.estimate;
import static com.kosa.chanzipup.domain.estimate.QEstimateConstructionType.estimateConstructionType;
import static com.kosa.chanzipup.domain.estimate.QEstimatePrice.estimatePrice;
import static com.kosa.chanzipup.domain.estimate.QEstimateRequest.estimateRequest;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstimateQueryService {
    private final JPAQueryFactory factory;

    public List<EstimateRequestResponse> getEstimateRequestResponsesOn(String companyEmail, EstimateRequestStatus status) {

        List<EstimateRequest> fetch = factory.select(estimateRequest)
                .from(estimateRequest) // 1
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin() // n - 1
                .where(estimateRequest.status.eq(status))
                .fetch();


        // 현재 업체 회원이 견적서를 보낸 requestId의 모든 id
        List<Long> writeEstimateRequestIds = factory.select(estimate)
                .from(estimate)
                .leftJoin(estimate.estimateRequest, estimateRequest)
                .leftJoin(estimate.company, company)
                .where(company.email.eq(companyEmail))
                .fetch()
                .stream()
                .map(estimate -> estimate.getEstimateRequest().getId())
                .toList();


        return fetch.stream()
                .map(estimateRequest -> new EstimateRequestResponse(estimateRequest, writeEstimateRequestIds.contains(estimateRequest.getId())))
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
                .map(estimate -> new EstimateRequestResponse(estimate))
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

    public List<EstimateRequestResponse> getAllEstimateRequestByUser(String userEmail, EstimateRequestStatus status) {

        List<EstimateRequest> requests = factory.select(estimateRequest)
                .from(estimateRequest)
                .leftJoin(estimateRequest.member, member).fetchJoin() // 1
                .leftJoin(estimateRequest.buildingType, buildingType).fetchJoin() // 1
                .leftJoin(estimateRequest.constructionTypes, estimateConstructionType).fetchJoin() // n
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin()
                .where(member.email.eq(userEmail),
                        estimateRequest.status.eq(status))
                .fetch();

        return requests.stream()
                .map(estimateRequest -> new EstimateRequestResponse(estimateRequest, false))
                .toList();
    }

    public List<SimpleEstimateResponse> findAllEstimateSimpleOnEstimateRequest(Long requestId) {
        // estimate
        List<Estimate> fetch = factory.select(estimate)
                .from(estimate) // 1
                .leftJoin(estimate.company, company)
                .leftJoin(estimate.estimateRequest, estimateRequest).fetchJoin() // 1
                .leftJoin(estimate.estimatePrices, estimatePrice).fetchJoin()
                .where(estimateRequest.id.eq(requestId), estimate.estimateStatus.eq(EstimateStatus.SENT)) // requestId 에 대한 요청이면서 업체가 보낸 견적이면
                .fetch();

        // null
        Map<Company, List<Estimate>> companyEstimates = fetch.stream()
                .collect(groupingBy(Estimate::getCompany, toList()));

        // 회사 정보
        return fetch.stream()
                .map(estimate -> new SimpleEstimateResponse(estimate, companyEstimates))
                .toList();
    }

    public EstimateDetailResponse getEstimateDetailOf(Long requestId, Long estimateId,
                                                      EstimateStatus status, EstimateRequestStatus requestStatus) {
        Optional<Estimate> findEstimate = Optional.of(
                factory.select(estimate)
                .from(estimate)
                .leftJoin(estimate.company, company).fetchJoin()
                .leftJoin(estimate.estimateRequest, estimateRequest).fetchJoin() // 1
                .leftJoin(estimate.estimatePrices, estimatePrice).fetchJoin()
                .leftJoin(estimatePrice.constructionType, estimateConstructionType).fetchJoin()
                .leftJoin(estimateConstructionType.constructionType, constructionType).fetchJoin()
                .where(estimateRequest.id.eq(requestId),
                        estimate.id.eq(estimateId),
                        estimate.estimateStatus.eq(status),
                        estimateRequest.status.eq(requestStatus))
                .fetchOne()
        );


        return findEstimate
                .map(EstimateDetailResponse::new)
                .orElse(null);
    }

    public EstimateDetailResponse getAcceptedEstimateDetailOf(Long requestId) {
        EstimateRequest request = factory.selectFrom(estimateRequest)
                .leftJoin(estimateRequest.estimates, estimate).fetchJoin()
                .where(estimateRequest.id.eq(requestId), estimate.estimateStatus.eq(ACCEPTED))
                .fetchOne();

        Optional<Estimate> acceptedEstimate = request.getEstimates()
                .stream()
                .filter(estimate -> estimate.getEstimateStatus() == ACCEPTED)
                .findAny();

        if (acceptedEstimate.isPresent()) {
            Estimate findEstimate = acceptedEstimate.get();
            return getEstimateDetailOf(requestId, findEstimate.getId(), ACCEPTED, ONGOING);
        }

        return null;
    }
}

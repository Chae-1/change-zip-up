package com.kosa.chanzipup.api.estimate.service;


import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestCreateResponse;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.estimate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateRequestService {

    private final EstimateRequestRepository estimateRequestRepository;
    private final MemberRepository memberRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final CompanyRepository companyRepository;
    private final EstimateRepository estimateRepository;
    private final EstimateConstructionTypeRepository estimateConstructionTypeRepository;


    @Transactional
    public EstimateRequestCreateResponse createEstimateRequest(EstimateRequestDTO estimateRequestDTO, String email) {
        // 이메일로 회원 정보 조회
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 건물 유형 조회
        BuildingType findBuildingType = buildingTypeRepository.findById(estimateRequestDTO.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("건물 유형이 존재하지 않습니다."));

        // 고유 식별자 생성
        String identification = generateIdentification();

        List<Long> constructionTypeIds = estimateRequestDTO.getConstructionTypeIds();
        List<ConstructionType> constructionTypes = constructionTypeRepository.findByIdIn(constructionTypeIds);

        // Estimate 객체 생성
        EstimateRequest estimate = EstimateRequest.builder()
                .identification(identification)
                .schedule(estimateRequestDTO.getSchedule())
                .status(EstimateRequestStatus.WAITING)
                .budget(estimateRequestDTO.getBudget())
                .address(estimateRequestDTO.getAddress())
                .detailedAddress(estimateRequestDTO.getDetailedAddress())
                .measureDate(estimateRequestDTO.getMeasureDate())
                .floor(estimateRequestDTO.getFloor())
                .buildingType(findBuildingType)
                .member(findMember)
                .regDate(LocalDateTime.now())
                .constructionTypes(constructionTypes)
                .build();

        estimateRequestRepository.save(estimate);
        return new EstimateRequestCreateResponse(constructionTypeIds, estimateRequestDTO.getAddress());
    }

    // 고유 식별자 생성 메서드 (현재 연도 + 월 + 일 + 5자리 정수)
    private String generateIdentification() {
        LocalDate currentDate = LocalDate.now();
        // 20241011_99999
        String currentYearMonthDay = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // todo: 동시성 문제 발생 락 사용으로 수정
        long count = estimateRequestRepository.count(); // 현재까지 저장된 견적의 수를 조회

        String nextId = String.format("%05d", count + 1); // 000001부터 시작해 하나씩 증가
        return currentYearMonthDay + nextId;
    }

    // 업체에 의해 새로운 견적이 고객에게 전송된다.
    @Transactional
    public void writePricesOfNewEstimate(String email, Long estimateRequestId, Map<Long, Integer> constructionPrices) {

        EstimateRequest request = estimateRequestRepository.findByIdWithAll(estimateRequestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요청 정보입니다."));

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업 정보입니다."));

        List<EstimateConstructionType> constructionTypes = estimateConstructionTypeRepository
                .findAllByIdWithConstructionType(constructionPrices.keySet());

        // 1.기존 견적이 존재하지 않으면 업체에 의해 새로운 견적이 고객에게 전송된다.
        // 2. 고객이 보냈던 견적이 존재하면 가격 정보에 대한 응답을 업데이트하여 고객에게 전송한다.
        estimateRepository.findByCompanyEmailAndEstimateRequestId(email, estimateRequestId)
                .ifPresentOrElse((estimate) -> {
                    estimate.updatePrices(constructionTypes, constructionPrices);
                }, () -> {
                    Estimate estimate = Estimate.sent(company, request, constructionTypes, constructionPrices);
                    estimateRepository.save(estimate);
                });

    }

    @Transactional
    public boolean cancelRequest(Long estimateRequestId) {
        // 1. 시공이 마음에 안들어서 취소할 수 있다.
        EstimateRequest estimateRequest = estimateRequestRepository.findByIdWithSpecifiedEstimate(estimateRequestId,
                        EstimateStatus.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시공 요청입니다."));

        estimateRequest.cancel();

        return true;
    }


    @Transactional
    public boolean completeRequest(Long estimateRequestId) {
        EstimateRequest estimateRequest = estimateRequestRepository.findByIdWithSpecifiedEstimate(estimateRequestId,
                        EstimateStatus.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시공 요청입니다."));

        estimateRequest.complete();
        return true;
    }

    @Transactional
    public void updateStatusToWrittenReview(Long requestId) {
        EstimateRequest estimateRequest = estimateRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청을 찾을 수 없습니다. ID: " + requestId));

        estimateRequest.writtenReview();
    }
}

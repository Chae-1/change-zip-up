package com.kosa.chanzipup.api.estimate.service;


import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequestDTO;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateRequestResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Transactional
    public void createEstimate(EstimateRequestDTO estimateRequestDTO, String email) {
        // 이메일로 회원 정보 조회
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 건물 유형 조회
        BuildingType findBuildingType = buildingTypeRepository.findById(estimateRequestDTO.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("건물 유형이 존재하지 않습니다."));

        // 고유 식별자 생성
        String identification = generateIdentification();

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
                .build();

        // 시공 유형 추가
        List<Long> constructionTypeIds = estimateRequestDTO.getConstructionTypeIds();
        for (Long constructionTypeId : constructionTypeIds) {
            // ConstructionType 조회
            ConstructionType constructionType = constructionTypeRepository.findById(constructionTypeId)
                .orElseThrow(() -> new IllegalArgumentException("시공 유형이 존재하지 않습니다."));

            // EstimateConstructionType 생성
            EstimateConstructionType estimateConstructionType = new EstimateConstructionType(constructionType, estimate);
            estimate.addConstructionType(estimateConstructionType); // Estimate에 추가
        }

        // Estimate 저장
        estimateRequestRepository.save(estimate);
    }

    // 고유 식별자 생성 메서드 (현재 연도 + 월 + 일 + 5자리 정수)
    private String generateIdentification() {
        LocalDate currentDate = LocalDate.now();
        // 20241011_99999
        String currentYearMonthDay = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 동시성 문제 발생
        // 락 사용
        long count = estimateRequestRepository.count(); // 현재까지 저장된 견적의 수를 조회

        String nextId = String.format("%05d", count + 1); // 000001부터 시작해 하나씩 증가
        return currentYearMonthDay + nextId;
    }

    @Transactional
    public void writePrices(String email, Long estimateRequestId, Map<Long, Integer> constructionPrices) {

        EstimateRequest request = estimateRequestRepository.findById(estimateRequestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요청 정보입니다."));

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업 정보입니다."));

        Estimate estimate = Estimate.send(company, request);
//
//        constructionPrices
//                .entrySet()
//                .stream()
//                .map(entry -> {
//                    new EstimatePrice(entry.getKey())
//                })





    }
}

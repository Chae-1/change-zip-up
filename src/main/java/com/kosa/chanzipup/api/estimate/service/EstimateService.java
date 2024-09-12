package com.kosa.chanzipup.api.estimate.service;


import com.kosa.chanzipup.api.estimate.controller.request.EstimateRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResponse;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.buildingtype.BuildingType;
import com.kosa.chanzipup.domain.buildingtype.BuildingTypeRepository;
import com.kosa.chanzipup.domain.constructiontype.ConstructionType;
import com.kosa.chanzipup.domain.constructiontype.ConstructionTypeRepository;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateConstructionType;
import com.kosa.chanzipup.domain.estimate.EstimateRepository;
import com.kosa.chanzipup.domain.estimate.EstimateStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final MemberRepository memberRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final ConstructionTypeRepository constructionTypeRepository;

    @Transactional
    public void createEstimate(EstimateRequest estimateRequest, String email) {
        // 이메일로 회원 정보 조회
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 건물 유형 조회
        BuildingType findBuildingType = buildingTypeRepository.findById(estimateRequest.getBuildingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("건물 유형이 존재하지 않습니다."));

        // 고유 식별자 생성
        String identification = generateIdentification();

        // Estimate 객체 생성
        Estimate estimate = Estimate.builder()
                .identification(identification)
                .estimateStatus(EstimateStatus.COUNSELING.getStatus())
                .schedule(estimateRequest.getSchedule())
                .budget(estimateRequest.getBudget())
                .address(estimateRequest.getAddress())
                .detailedAddress(estimateRequest.getDetailedAddress())
                .measureDate(estimateRequest.getMeasureDate())
                .floor(estimateRequest.getFloor())
                .buildingType(findBuildingType)
                .member(findMember)
                .regDate(LocalDate.now())
                .build();

        // 시공 유형 추가
        List<Long> constructionTypeIds = estimateRequest.getConstructionTypeIds();
        for (Long constructionTypeId : constructionTypeIds) {
            // ConstructionType 조회
            ConstructionType constructionType = constructionTypeRepository.findById(constructionTypeId)
                .orElseThrow(() -> new IllegalArgumentException("시공 유형이 존재하지 않습니다."));

            // EstimateConstructionType 생성
            EstimateConstructionType estimateConstructionType = new EstimateConstructionType(constructionType, estimate);
            estimate.addConstructionType(estimateConstructionType); // Estimate에 추가
        }

        // Estimate 저장
        estimateRepository.save(estimate);
    }

    // 고유 식별자 생성 메서드 (현재 연도 + 월 + 일 + 5자리 정수)
    private String generateIdentification() {
        LocalDate currentDate = LocalDate.now();
        String currentYearMonthDay = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = estimateRepository.count(); // 현재까지 저장된 견적의 수를 조회
        String nextId = String.format("%05d", count + 1); // 000001부터 시작해 하나씩 증가
        return currentYearMonthDay + nextId;
    }

//    public List<EstimateResponse> findAll(String email) {
//        Member findMember = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//
//        // 1번 query result == 2000 N개를 조회하는 쿼리가 한번 나간다.
//        List<Estimate> estimates = estimateRepository.findAllWithMember(); // 2000개 조회
//        List<EstimateResponse> responses = new ArrayList<>();
//        for (Estimate estimate : estimates) {
//            Member member = estimate.getMember(); //
//            // 로그인한 유저와 작성한 유저가 같다.
//            if (member.getId() == findMember.getId()) {
//                responses.add(EstimateResponse.write("content", true));
//                continue;
//            }
//            // 멤버 정보를 최소한으로 유지하고 있을 경우 사용해하는 방식
//            responses.add(EstimateResponse.noWrite("content", false));
//        }
//        return responses;
//    }
}

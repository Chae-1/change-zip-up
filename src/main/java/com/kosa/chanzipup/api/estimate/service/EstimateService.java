package com.kosa.chanzipup.api.estimate.service;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResult;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.estimate.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EstimateService {
    private final EstimateRequestRepository estimateRequestRepository;
    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final EstimateRepository estimateRepository;

    // 회사에 요청 견적을 보낸다.
    @Transactional
    public EstimateResult sendEstimateToCompany(String userEmail,
                                      EstimateRegisterRequest request) {
        // 1. 요청한 유저
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 없음."));

        // 2. 회사 정보 가져오기
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("회사 정보 없음."));

        // 3. 견적 요청 정보 가져오기
        EstimateRequest estimateRequest = estimateRequestRepository.findByIdWithUser(request.getEstimateRequestId())
                .orElseThrow(() -> new IllegalArgumentException("요청 정보 없음."));

        // 4. 유저가 등록한 요청인지 확인
        if (isNotRequestedMember(estimateRequest, member)) {
            throw new IllegalArgumentException("유저가 등록한 요청이 아닙니다.");
        }

        // 5. 견적 요청을 회사에 전송
        Estimate estimate = Estimate.waiting(company, estimateRequest);
        estimateRepository.save(estimate);

        return EstimateResult.of(company, estimateRequest, estimate);
    }

    // 요청한 유저가 맞는지 확인
    private boolean isNotRequestedMember(EstimateRequest estimateRequest, Member member) {
        return !(estimateRequest.getMember() == member);
    }

    // 특정 업체에게 온 견적 요청을 조회
    public List<EstimateResult> getWaitingEstimatesByCompanyEmail(String companyEmail) {
        Company company = companyRepository.findByEmail(companyEmail)
                .orElseThrow(() -> new IllegalArgumentException("업체 정보 없음."));

        List<Estimate> estimates = estimateRepository.findAllWaitingByCompany(company);

        return estimates.stream()
                .map(estimate -> EstimateResult.of(estimate.getCompany(), estimate.getEstimateRequest(), estimate))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstimateRequest getLatestEstimateRequestByUserEmail(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 없음."));

        return estimateRequestRepository.findFirstByMemberOrderByRegDateDesc(member)
                .orElseThrow(() -> new IllegalArgumentException("최근 견적 요청 정보 없음."));
    }

    @Transactional
    public void cancelEstimateByRequestIdAndCompanyEmail(Long estimateRequestId, String companyEmail) {
        // companyEmail을 통해 회사 정보 조회
        Company company = companyRepository.findByEmail(companyEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체 정보 없음: " + companyEmail));

        // estimateRequestId와 company 정보를 기반으로 Estimate 찾기
        Estimate estimate = estimateRepository.findByEstimateRequestIdAndCompany(estimateRequestId, company)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청에 대한 견적이 존재하지 않거나 권한이 없습니다: " + estimateRequestId));

        // 상태를 CANCELLATION로 업데이트
        estimate.updateEstimateStatus(EstimateStatus.CANCELLATION);

        // 업데이트된 견적을 저장
        estimateRepository.save(estimate);
    }

    @Transactional
    public void approvalEstimateByRequestIdAndCompanyEmail(Long estimateRequestId, String companyEmail) {
        // companyEmail을 통해 회사 정보 조회
        Company company = companyRepository.findByEmail(companyEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체 정보 없음: " + companyEmail));

        // estimateRequestId와 company 정보를 기반으로 Estimate 찾기
        Estimate estimate = estimateRepository.findByEstimateRequestIdAndCompany(estimateRequestId, company)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청에 대한 견적이 존재하지 않거나 권한이 없습니다: " + estimateRequestId));

        // 상태를 ONGOING로 업데이트
        estimate.updateEstimateStatus(EstimateStatus.ONGOING);

        // 업데이트된 견적을 저장
        estimateRepository.save(estimate);
    }
}

package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.api.estimate.controller.request.EstimateRegisterRequest;
import com.kosa.chanzipup.api.estimate.controller.response.EstimateResult;
import com.kosa.chanzipup.domain.account.company.Company;
import com.kosa.chanzipup.domain.account.company.CompanyRepository;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public EstimateResult sendEstimateToCompany(String userEmail,
                                      EstimateRegisterRequest request) {
        // 1. 요청한 유저
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 없음."));
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("회사 정보 없음."));
        EstimateRequest estimateRequest = estimateRequestRepository.findByIdWithUser(request.getEstimateRequestId())
                .orElseThrow(() -> new IllegalArgumentException("요청 정보 없음."));

        if (isNotRequestedMember(estimateRequest, member)) {
            throw new IllegalArgumentException("유저가 등록한 요청이 아닙니다.");
        }

        // 2. 정상적이면 estimateRequest를 company에 전송.
        Estimate estimate = Estimate.waiting(company, estimateRequest);
        estimateRepository.save(estimate);

        return EstimateResult.of(company, estimateRequest, estimate);
    }

    private boolean isNotRequestedMember(EstimateRequest estimateRequest, Member member) {
        return !(estimateRequest.getMember() == member);
    }


}

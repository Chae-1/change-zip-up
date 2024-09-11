package com.kosa.chanzipup.api.estimate.service;


import com.kosa.chanzipup.api.estimate.controller.response.EstimateResponse;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.estimate.Estimate;
import com.kosa.chanzipup.domain.estimate.EstimateRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final MemberRepository memberRepository;

    public List<EstimateResponse> findAll(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 1번 query result == 2000 N개를 조회하는 쿼리가 한번 나간다.
        List<Estimate> estimates = estimateRepository.findAllWithMember(); // 2000개 조회
        List<EstimateResponse> responses = new ArrayList<>();
        for (Estimate estimate : estimates) {
            Member member = estimate.getMember(); //
            // 로그인한 유저와 작성한 유저가 같다.
            if (member.getId() == findMember.getId()) {
                responses.add(EstimateResponse.write("content", true));
                continue;
            }
            // 멤버 정보를 최소한으로 유지하고 있을 경우 사용해하는 방식
            responses.add(EstimateResponse.noWrite("content", false));
        }
        return responses;
    }

}

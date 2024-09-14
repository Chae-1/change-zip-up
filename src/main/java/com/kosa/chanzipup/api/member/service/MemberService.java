package com.kosa.chanzipup.api.member.service;

import com.kosa.chanzipup.api.member.controller.request.MemberRegisterRequest;
import com.kosa.chanzipup.api.member.controller.response.MemberRegisterResponse;
import com.kosa.chanzipup.application.VerificationCode;
import com.kosa.chanzipup.domain.account.AccountRole;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    // final -> 필드를 한번만 초기화(무변경 보장)

    // 로컬 회원 계정 가입
    @Transactional
    public MemberRegisterResponse registerMember(MemberRegisterRequest request) {
        // 응답 데이터를 담고 있는 객체(DTO, Data Transfer Object)를 반환 타입으로 사용하면,
        // 그 객체에 담긴 데이터가 구조화되어 서버에서 클라이언트에게 전달
        // 이 메서드는 회원이 보낸 등록 요청을 파라미터로 받고 서버에서 정상적으로 가입이 되었다는 응답을 반환타입으로 받는다

        // 이메일 확인
        if (isEmailDuplicated(request.getEmail())) {
        // Optional의 isPresent()메서드를 활용하여 값이 존재하는지 확인
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            // 잘못된 인수나 부적절한 값이 전달되었을 때 이 예외 처리를 사용하여 에외처리시 메세지를 남길 수 있다.
        }

        Member member =  Member.ofLocal(AccountRole.USER, request.getEmail(), encoder.encode(request.getPassword()), request.getPhoneNumber(),
                request.getMemberType(), request.getNickName(), request.getName());

        memberRepository.save(member);

        return MemberRegisterResponse.of(member.getEmail(), request.getName());
    }

    // 이메일 중복 확인
    public boolean isEmailDuplicated(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }
}

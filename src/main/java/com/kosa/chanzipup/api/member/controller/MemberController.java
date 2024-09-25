package com.kosa.chanzipup.api.member.controller;

import com.kosa.chanzipup.api.member.controller.request.EmailDuplicationCheckResponse;
import com.kosa.chanzipup.api.member.controller.request.MemberRegisterRequest;
import com.kosa.chanzipup.api.member.controller.request.MemberUpdateRequest;
import com.kosa.chanzipup.api.member.controller.response.MemberRegisterResponse;
import com.kosa.chanzipup.api.member.controller.response.MyPageResponse;
import com.kosa.chanzipup.api.member.service.MemberService;
import com.kosa.chanzipup.config.security.userdetail.UnifiedUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
// HTTP 요청을 처리하고 JSON과 같은 데이터를 반환
@RequiredArgsConstructor
// final 필드 자동 주입하는 생성자 생성
@RequestMapping("/api/member")
// 컨트롤러의 모든 엔드포인트
public class MemberController {

    private final MemberService memberService;

    // 로컬 회원 가입
    @PostMapping
    public ResponseEntity<MemberRegisterResponse> addLocalMember (@RequestBody @Valid MemberRegisterRequest registerRequest) {
        //HTTP 요청의 본문(body)에서 데이터를 가져와서 MemberRegisterRequest 객체로 변환
        MemberRegisterResponse savedMember = memberService.registerMember(registerRequest);
        return ResponseEntity.ok(savedMember);
        // HTTP 200 응답반환
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<EmailDuplicationCheckResponse> checkEmail(@RequestParam String email) {
        boolean isDuplicated = memberService.isEmailDuplicated(email);
        EmailDuplicationCheckResponse response = new EmailDuplicationCheckResponse(isDuplicated);
        return ResponseEntity.ok(response);
    }
    // Optional로 반환이 불가능 -> 이메일 중복 확인하는 EmailDuplicationCheckResponse DTO를 생성하여 이를 반환

    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> myPage(@AuthenticationPrincipal UnifiedUserDetails userDetails) {
        return ResponseEntity.ok(memberService.getMemberDetail(userDetails.getUsername()));
    }

    @PatchMapping("/mypage")
    public ResponseEntity<MyPageResponse> afterUpdateMyPage(@AuthenticationPrincipal UnifiedUserDetails userDetails,
                                                            @RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(memberService.updateMember(userDetails.getUsername(), request));
    }
}

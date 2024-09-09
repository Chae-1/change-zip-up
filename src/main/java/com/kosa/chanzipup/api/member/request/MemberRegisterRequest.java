package com.kosa.chanzipup.api.member.request;

import com.kosa.chanzipup.domain.account.member.SocialType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRegisterRequest {
    // 회원 가입 요청 DTO
    @NotBlank(message = "이메일은 필수 입력 항목입니다")
    // String 타입에만 사용 가능, null+공백인지 데이터 유효성 검사
    // 이와 달리 @Notnull은 모든 객체유형 사용 가능, null인지 데이터 유효성 검사
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    // Email 형식인지 검사
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    private String password;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다")
    private String phoneNumber;
    @NotBlank(message = "이름을 필수 입력 항목입니다")
    private String name;
    @NotBlank(message = "닉네임을 필수 입력 항목입니다")
    private String nickName;
    private SocialType socialType;
}

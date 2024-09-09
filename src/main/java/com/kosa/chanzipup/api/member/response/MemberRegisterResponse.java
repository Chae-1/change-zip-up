package com.kosa.chanzipup.api.member.response;

import lombok.Getter;

@Getter
// getEmail..등등 자동으로 getter메서드 생성
public class MemberRegisterResponse {
    // 데이터 전달하기 위한 DTO라서 데이터베이스와 직접 매핑하는 Entity와 다름
    // 회원 가입 여부에 따른 응답 DTO

    private String email;
    private String name;

    private MemberRegisterResponse(String email, String name) {
        //자바는 정적 언어 타입이라서 파라미터에 필수로 타입을 적는 게 좋음
        this.email = email;
        this.name = name;
        //객체를 생성할 때 생성자는 자동으로 객체를 반환하는 역할을 수행하기 때문에 반환할 것이 없다
    }

    public static MemberRegisterResponse of(String email, String name) {
        return new MemberRegisterResponse(email, name);
    }
    // 첫번째 메서드는 필드 초기화(화면에 입력한 email과 name를 각각 필드에 저장하는 것) 담당
    // 두번째 메서드는 첫번째 메서드를 호출하여 객체생성
    // 생성자는 new를 통해서 객체를 생성하는데 정적 팩토리 메서드를 통해서 굳이 생성자를 사용하지 않고 객체를 생성하고자 두번째 메서드를 만듬
}

package com.kosa.chanzipup.api.member.controller.response;

import com.kosa.chanzipup.domain.account.member.Member;
import lombok.Getter;

@Getter
public class MyPageResponse {
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;

    public MyPageResponse(Member member) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
    }
}

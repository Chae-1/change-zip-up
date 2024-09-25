package com.kosa.chanzipup.api.member.controller.request;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String password;
    private String phoneNumber;
}

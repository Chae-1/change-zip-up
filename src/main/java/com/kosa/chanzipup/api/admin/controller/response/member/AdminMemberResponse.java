package com.kosa.chanzipup.api.admin.controller.response.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AdminMemberResponse {
    private Long id;
    private String name;
    private String nickName;
    private String email;
    private LocalDate createAt;
    private String phoneNumber;
    private boolean isVerified;
}

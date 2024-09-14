package com.kosa.chanzipup.api.member.service;

import static org.junit.jupiter.api.Assertions.*;

import com.kosa.chanzipup.api.member.controller.request.MemberRegisterRequest;
import com.kosa.chanzipup.api.member.controller.response.MemberRegisterResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @DisplayName("")
    @Test
    void registerMember() {
        // given
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest();
        memberRegisterRequest.setEmail("coguddlf1000@naver.com");
        memberRegisterRequest.setPassword("1234");
        // when
        MemberRegisterResponse memberRegisterResponse = memberService.registerMember(memberRegisterRequest);

        // then
    }
}
package com.kosa.chanzipup.api.controller.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiMemberController {

    @GetMapping("/api/test")
    public String naver(HttpServletRequest request) {
        // 이곳까지 호출됐으면 로그인 성공
        log.info("naver callback 호출 완료");
        Iterator<String> iterator = request.getParameterNames().asIterator();

        return "callback";
    }

    @GetMapping("/api/asd")
    public String naver1(HttpServletRequest request) {
        // 이곳까지 호출됐으면 로그인 성공
        log.info("naver callback 호출 완료");
        Iterator<String> iterator = request.getParameterNames().asIterator();

        return "callback";
    }
}

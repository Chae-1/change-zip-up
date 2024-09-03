package com.kosa.chanzipup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OAuthTestController {
    @GetMapping("/")
    public String home() {
        return "APIExamNaverLogin";
    }
}

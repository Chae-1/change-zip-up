package com.kosa.chanzipup.api.controller;

import com.kosa.chanzipup.api.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public ApiResponse<String> hello(HttpServletResponse response) {
        Cookie cookie1 = new Cookie("Authentication", "dd");
        Cookie cookie2 = new Cookie("Authentication-Refresh", "dd");
        return ApiResponse.of("hello");
    }
}

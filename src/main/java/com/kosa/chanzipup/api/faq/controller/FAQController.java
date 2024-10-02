package com.kosa.chanzipup.api.faq.controller;

import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQListResponseDto;
import com.kosa.chanzipup.api.faq.service.FAQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faq")
public class FAQController {

    private final FAQService faqService;

    @GetMapping("/list")
    public ResponseEntity<List<AdminFAQListResponseDto>> listFAQ() {
        List<AdminFAQListResponseDto> faqs = faqService.getFAQList();
        return ResponseEntity.ok(faqs);
    }
}

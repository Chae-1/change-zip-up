package com.kosa.chanzipup.api.faq.service;

import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.faq.AdminFAQListResponseDto;
import com.kosa.chanzipup.domain.faq.FAQ;
import com.kosa.chanzipup.domain.faq.FAQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FAQService {

    private final FAQRepository faqRepository;

    // 전체 FAQ 조회
    public List<AdminFAQListResponseDto> getFAQList() {
        List<FAQ> faqs = faqRepository.findAll();
        return faqs.stream()
                .map(faq -> new AdminFAQListResponseDto(
                        faq.getId(),
                        faq.getTitle(),
                        faq.getContent(),
                        faq.getAuthorNickName(),
                        LocalDate.from(faq.getUpdateDate())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public AdminFAQDetailResponseDto getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 FAQ를 찾을 수 없습니다."));

        return new AdminFAQDetailResponseDto(
                faq.getId(),
                faq.getTitle(),
                faq.getAuthorNickName(),
                LocalDate.from(faq.getUpdateDate()),
                faq.getContent()
        );
    }
}

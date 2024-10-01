package com.kosa.chanzipup.api.admin.controller.response.faq;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FAQListResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDate updateDate;

    @Builder
    public FAQListResponseDto(Long id, String title, String content, String authorName, LocalDate updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.updateDate = updateDate;
    }
}

package com.kosa.chanzipup.api.admin.controller.response.faq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FAQDetailResponseDto {
    private Long id;
    private String title;
    private String authorName;
    private LocalDate updateDate;
    private String content;
}

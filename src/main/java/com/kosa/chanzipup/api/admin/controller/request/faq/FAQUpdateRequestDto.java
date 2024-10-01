package com.kosa.chanzipup.api.admin.controller.request.faq;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FAQUpdateRequestDto {
    private String title;
    private String content;
}

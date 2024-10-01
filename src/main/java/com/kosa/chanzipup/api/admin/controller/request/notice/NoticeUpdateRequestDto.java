package com.kosa.chanzipup.api.admin.controller.request.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeUpdateRequestDto {
    private String title;
    private String content;
}
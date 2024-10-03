package com.kosa.chanzipup.api.admin.controller.request.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminNoticeUpdateRequestDto {
    private String title;
    private String content;
}
package com.kosa.chanzipup.api.admin.controller.response.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminNoticeDetailResponseDto {
    private Long id;
    private String title;
    private String authorName;
    private LocalDate updateDate;
    private String content;
}

package com.kosa.chanzipup.api.admin.controller.response.notice;

import com.kosa.chanzipup.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDate createDate;

    @Builder
    public NoticeResponseDto(Long id, String title, String content, LocalDate createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public static NoticeResponseDto fromEntity(Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createDate(LocalDate.from(notice.getCreateDate()))
                .build();
    }
}

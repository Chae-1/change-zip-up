package com.kosa.chanzipup.api.admin.controller.response.notice;

import com.kosa.chanzipup.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeListResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDate updateDate;

    @Builder
    public NoticeListResponseDto(Long id, String title, String content, String authorName, LocalDate updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.updateDate = updateDate;
    }

    public static NoticeListResponseDto fromEntity(Notice notice) {
        return NoticeListResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .updateDate(LocalDate.from(notice.getCreateDate()))
                .build();
    }
}

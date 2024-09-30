package com.kosa.chanzipup.api.admin.controller.request.notice;

import com.kosa.chanzipup.domain.notice.Notice;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeRequestDto {

    private String title;

    private String content;

    @Builder
    public NoticeRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

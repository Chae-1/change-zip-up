package com.kosa.chanzipup.api.admin.service.notice;

import com.kosa.chanzipup.api.admin.controller.request.notice.NoticeRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.NoticeResponseDto;
import com.kosa.chanzipup.domain.notice.Notice;
import com.kosa.chanzipup.domain.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지 사항 생성
    @Transactional
    public void createNotice(NoticeRequestDto noticeRequestDto, String email) {
        Notice notice = Notice.builder()
                .title(noticeRequestDto.getTitle())
                .content(noticeRequestDto.getContent())
                .authorEmail(email)
                .build();

        noticeRepository.save(notice);
    }
}

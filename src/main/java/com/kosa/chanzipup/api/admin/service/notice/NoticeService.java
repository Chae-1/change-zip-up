package com.kosa.chanzipup.api.admin.service.notice;

import com.kosa.chanzipup.api.admin.controller.request.notice.NoticeCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.NoticeDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.NoticeListResponseDto;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.notice.Notice;
import com.kosa.chanzipup.domain.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NoticeService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    // 공지 사항 생성
    @Transactional
    public void createNotice(NoticeCreateRequestDto noticeCreateRequestDto, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Notice notice = Notice.builder()
                .title(noticeCreateRequestDto.getTitle())
                .content(noticeCreateRequestDto.getContent())
                .email(email)
                .member(findMember)
                .build();

        noticeRepository.save(notice);
    }

    public List<NoticeListResponseDto> getNoticeList() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(notice -> new NoticeListResponseDto(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getContent(),
                        notice.getAuthorNickName(),
                        LocalDate.from(notice.getUpdateDate())
                ))
                .collect(Collectors.toList());
    }

    // 공지사항 ID로 공지사항을 조회합니다.
    public NoticeDetailResponseDto getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다."));

        return new NoticeDetailResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getAuthorNickName(),
                LocalDate.from(notice.getUpdateDate()),
                notice.getContent()
        );
    }
}

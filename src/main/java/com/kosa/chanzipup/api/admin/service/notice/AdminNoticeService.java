package com.kosa.chanzipup.api.admin.service.notice;

import com.kosa.chanzipup.api.admin.controller.request.notice.AdminNoticeCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.notice.AdminNoticeUpdateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeListResponseDto;
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
public class AdminNoticeService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    // 공지 사항 생성
    @Transactional
    public void createNotice(AdminNoticeCreateRequestDto adminNoticeCreateRequestDto, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Notice notice = Notice.builder()
                .title(adminNoticeCreateRequestDto.getTitle())
                .content(adminNoticeCreateRequestDto.getContent())
                .email(email)
                .member(findMember)
                .build();

        noticeRepository.save(notice);
    }

    // 전체 공지사항 조회
    public List<AdminNoticeListResponseDto> getNoticeList() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(notice -> new AdminNoticeListResponseDto(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getContent(),
                        notice.getAuthorNickName(),
                        LocalDate.from(notice.getUpdateDate())
                ))
                .collect(Collectors.toList());
    }

    // 공지사항 ID로 공지사항을 조회합니다.
    public AdminNoticeDetailResponseDto getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다."));

        return new AdminNoticeDetailResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getAuthorNickName(),
                LocalDate.from(notice.getUpdateDate()),
                notice.getContent()
        );
    }

    @Transactional
    public void patchNotice(Long id, AdminNoticeUpdateRequestDto adminNoticeUpdateRequestDto, String email) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다."));

        // 작성자 확인 (옵션: 관리자 권한 확인 등 추가 가능)
        if (!notice.getEmail().equals(email)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 필드가 null이 아닌 경우에만 업데이트
        if (adminNoticeUpdateRequestDto.getTitle() != null) {
            notice.updateTitle(adminNoticeUpdateRequestDto.getTitle());
        }

        if (adminNoticeUpdateRequestDto.getContent() != null) {
            notice.updateContent(adminNoticeUpdateRequestDto.getContent());
        }

        // JPA의 변경 감지 덕분에 별도로 save를 호출할 필요 없음
        log.info("공지사항 부분 수정 완료: ID = {}", id);
    }

    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다."));

        noticeRepository.delete(notice);
        log.info("공지사항 삭제 완료: ID = {}", id);
    }
}

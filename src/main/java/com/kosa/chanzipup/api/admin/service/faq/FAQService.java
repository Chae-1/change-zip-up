package com.kosa.chanzipup.api.admin.service.faq;

import com.kosa.chanzipup.api.admin.controller.request.faq.FAQCreateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.faq.FAQUpdateRequestDto;
import com.kosa.chanzipup.api.admin.controller.request.notice.NoticeUpdateRequestDto;
import com.kosa.chanzipup.api.admin.controller.response.faq.FAQDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.faq.FAQListResponseDto;
import com.kosa.chanzipup.domain.account.member.Member;
import com.kosa.chanzipup.domain.account.member.MemberRepository;
import com.kosa.chanzipup.domain.faq.FAQ;
import com.kosa.chanzipup.domain.faq.FAQRepository;
import com.kosa.chanzipup.domain.notice.Notice;
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
public class FAQService {

    private final MemberRepository memberRepository;
    private final FAQRepository faqRepository;

    // FAQ 생성
    @Transactional
    public void createFAQ(FAQCreateRequestDto faqCreateRequestDto, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        FAQ faq = FAQ.builder()
                .title(faqCreateRequestDto.getTitle())
                .content(faqCreateRequestDto.getContent())
                .email(email)
                .member(findMember)
                .build();

        faqRepository.save(faq);
    }

    // 전체 FAQ 조회
    public List<FAQListResponseDto> getFAQList() {
        List<FAQ> faqs = faqRepository.findAll();
        return faqs.stream()
                .map(faq -> new FAQListResponseDto(
                        faq.getId(),
                        faq.getTitle(),
                        faq.getContent(),
                        faq.getAuthorNickName(),
                        LocalDate.from(faq.getUpdateDate())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public FAQDetailResponseDto getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 FAQ를 찾을 수 없습니다."));

        return new FAQDetailResponseDto(
                faq.getId(),
                faq.getTitle(),
                faq.getAuthorNickName(),
                LocalDate.from(faq.getUpdateDate()),
                faq.getContent()
        );
    }

    @Transactional
    public void patchFAQ(Long id, FAQUpdateRequestDto faqUpdateRequestDto, String email) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공지사항을 찾을 수 없습니다."));

        // 작성자 확인 (옵션: 관리자 권한 확인 등 추가 가능)
        if (!faq.getEmail().equals(email)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 필드가 null이 아닌 경우에만 업데이트
        if (faqUpdateRequestDto.getTitle() != null) {
            faq.updateTitle(faqUpdateRequestDto.getTitle());
        }

        if (faqUpdateRequestDto.getContent() != null) {
            faq.updateContent(faqUpdateRequestDto.getContent());
        }

        // JPA의 변경 감지 덕분에 별도로 save를 호출할 필요 없음
        log.info("공지사항 부분 수정 완료: ID = {}", id);
    }

    @Transactional
    public void deleteFAQ(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 FAQ를 찾을 수 없습니다."));

        faqRepository.delete(faq);
        log.info("FAQ 삭제 완료: ID = {}", id);
    }
}

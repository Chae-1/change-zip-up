package com.kosa.chanzipup.api.notice.controller;

import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeDetailResponseDto;
import com.kosa.chanzipup.api.admin.controller.response.notice.AdminNoticeListResponseDto;
import com.kosa.chanzipup.api.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<List<AdminNoticeListResponseDto>> listNotice() {
        List<AdminNoticeListResponseDto> notices = noticeService.getNoticeList();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminNoticeDetailResponseDto> getNoticeById(@PathVariable Long id) {
        AdminNoticeDetailResponseDto adminNoticeDetailResponseDto = noticeService.getNoticeById(id);
        return ResponseEntity.ok(adminNoticeDetailResponseDto);
    }
}

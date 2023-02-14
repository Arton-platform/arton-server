package com.arton.backend.administer.announcement.adapter.in.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.ResponseDate;
import org.elasticsearch.action.admin.indices.dangling.find.FindDanglingIndexRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.announcement.application.port.in.ManageAnnouncementUseCase;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MangeAnnouncementController {

    private final ManageAnnouncementUseCase manageAnnouncementUseCase;

    // 공지사항 목록 조회(다건)
    @GetMapping("/all-announcement")
    public ResponseData<List<Announcement>> allAnnouncement() {
        return new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                manageAnnouncementUseCase.allAnnouncement());
    }

    // 공지사항 목록 조회(단건)
    @GetMapping("/announcement/{id}")
    public ResponseData<Announcement> announcement(@PathVariable("id") Long id) {
        return new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                manageAnnouncementUseCase.getAnnouncement(id));
    }

    // 공지사항 등록
    @PostMapping("/announcement-regist")
    public CommonResponse announcementRegist(Announcement announcement) {

        manageAnnouncementUseCase.announcementRegist(announcement);

        return CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
    }

    // 공지사항 수정
    @PatchMapping("/announcement-update")
    public CommonResponse announcementUpdate(Announcement announcement) {

        manageAnnouncementUseCase.announcementUpdate(announcement);

        return CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
    }

    // 공지사항 삭제
    @DeleteMapping("/announcement-delete/{id}")
    public CommonResponse announcementDelete(@PathVariable("id") Long id) {

        manageAnnouncementUseCase.announcementDelete(id);

        return CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
    }
}

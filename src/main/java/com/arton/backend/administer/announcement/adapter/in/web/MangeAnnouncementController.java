package com.arton.backend.administer.announcement.adapter.in.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.ResponseDate;
import org.elasticsearch.action.admin.indices.dangling.find.FindDanglingIndexRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.announcement.application.port.in.ManageAnnouncementUseCase;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.infra.shared.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MangeAnnouncementController {

    private final ManageAnnouncementUseCase manageAnnouncementUseCase;
    
    // 공지사항 목록 조회(다건)
    public ResponseData<List<Announcement>> allAnnouncement(){
        return new ResponseData<List<Announcement>>(
            "SUCCESS",
            HttpStatus.OK.value(),
            manageAnnouncementUseCase.allAnnouncement()
        );
    }
    // 공지사항 목록 조회(단건)
    // 공지사항 등록
    // 공지사항 수정
    // 공지사항 삭제
}

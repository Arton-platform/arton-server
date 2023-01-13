package com.arton.backend.announcement.adapter.in.web;

import com.arton.backend.announcement.application.port.in.SelectAllUseCase;
import com.arton.backend.announcement.application.port.in.SelectOneUseCase;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.infra.shared.common.ResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/announcement")
@Slf4j
public class AnnounceController {

    private final SelectAllUseCase selectAllUseCase;
    private final SelectOneUseCase selectOneUseCase;

    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<Announcement>>> announcementList(){
        log.info("[Announcement] : announcementList {}", "announcementList");
        ResponseData<List<Announcement>> response = new ResponseData(
          "SUCCESS",
                HttpStatus.OK.value(),
                selectAllUseCase.announcementList()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<List<Announcement>>> loadAnnouncementDetail(@PathVariable("id") Long id){
        log.info("[Announcement] : loadAnnouncementDetail - announcementId : {}", id);
        ResponseData<List<Announcement>> response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                selectOneUseCase.loadAnnouncementDetail(id)
        );
        return ResponseEntity.ok().body(response);
    }
}

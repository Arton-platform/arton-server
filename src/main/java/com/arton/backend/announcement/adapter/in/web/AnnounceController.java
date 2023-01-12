package com.arton.backend.announcement.adapter.in.web;

import com.arton.backend.announcement.application.port.in.SelectAllUseCase;
import com.arton.backend.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/announcement")
public class AnnounceController {

    private final SelectAllUseCase selectAllUseCase;

    @GetMapping("/list")
    public List<Announcement> announcementList(){
        return selectAllUseCase.announcementList();
    }
}

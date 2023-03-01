package com.arton.backend.user.adapter.in;

import com.arton.backend.search.application.port.in.UserSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserTask {

    private final UserSearchUseCase userSearchUseCase;

    @Scheduled(cron = "0 0 0 * * ?") // 1일 1회 00:00에 실행
    public void saveDocuments() {
        log.info("[SCHEDULE] {}", "execute performanceDocumentSchedule");
        userSearchUseCase.saveAll();
        log.info("[SCHEDULE] finished...");
    }
}

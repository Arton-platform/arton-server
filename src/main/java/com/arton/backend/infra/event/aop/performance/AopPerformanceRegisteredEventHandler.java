package com.arton.backend.infra.event.aop.performance;

import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateResponseDto;
import com.arton.backend.infra.event.aop.register.AopUserRegisteredEvent;
import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import com.arton.backend.performer.adapter.out.persistence.repository.PerformerRepository;
import com.arton.backend.performer.adapter.out.persistence.repository.PerformerRepositoryAdapter;
import com.arton.backend.performer.domain.Performer;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.arton.backend.infra.utils.MailTemplateUtils.replaceTemplateBody;

@Component
@RequiredArgsConstructor
public class AopPerformanceRegisteredEventHandler {
    private final PerformerRepositoryAdapter performerRepository;
    /**
     * @param event
     */
    @Async
    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AopPerformanceRegisteredEvent.class)
    void handle(AopPerformanceRegisteredEvent event) {
        PerformanceAdminCreateResponseDto responseDto = event.getValue();

        Long performanceId = responseDto.getPerformanceId();
        List<Long> artistIds = responseDto.getArtistIds();
        List<Performer> performers = new ArrayList<>();
        for (Long artistId : artistIds) {
            performers.add(Performer.builder().performance(performanceId).artist(artistId).build());
        }
        performerRepository.saveAll(performers);
    }
}

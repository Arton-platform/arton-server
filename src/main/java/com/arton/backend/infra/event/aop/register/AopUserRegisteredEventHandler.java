package com.arton.backend.infra.event.aop.register;

import com.arton.backend.infra.event.UserRegisteredEvent;
import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ObjectUtils;

import static com.arton.backend.infra.utils.MailTemplateUtils.replaceTemplateBody;

@Component
@RequiredArgsConstructor
public class AopUserRegisteredEventHandler {
    private final MailUseCase mailUseCase;
    private final EmailUseCase emailUseCase;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    /**
     * 회원가입 메일 처리
     * content ex: ${nickname}님 회원 가입을 축하드려요~
     * @param event
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AopUserRegisteredEvent.class)
    void handle(AopUserRegisteredEvent event) {
        // 회원가입 템플릿 확인
        Mail mail = mailUseCase.findMailByCode(MailCode.REGISTER);
        // 템플릿 없다면 발송 x
        if (ObjectUtils.isEmpty(mail)) {
            log.info("[AUTO_MAILING_BODY] 해당 이벤트에 관한 메일 템플릿이 존재하지 않습니다.");
            return;
        }
        String content = mail.getContent();
        User user = event.getValue();
        // 메일 수신 동의 X
        if (user.getTermsAgree().equals("N")) {
            return;
        }
        content = replaceTemplateBody(content, user);
        log.info("[AUTO_MAILING_BODY] {}", content);
        MailDto mailDto = MailDto.builder().subject(mail.getSubject()).receiver(user.getEmail()).messageBody(content).build();
        emailUseCase.sendMailByHTML(mailDto);
    }
}

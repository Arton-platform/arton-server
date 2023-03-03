package com.arton.backend.infra.event;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.infra.utils.SuperClassReflectionUtils;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler {
    private final MailUseCase mailUseCase;
    private final EmailUseCase emailUseCase;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    /**
     * 회원가입 메일 처리
     * content ex: ${nickname}님 회원 가입을 축하드려요~
     * @param event
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = UserRegisteredEvent.class)
    void handle(UserRegisteredEvent event) {
        // 회원가입 템플릿 확인
        Mail mail = mailUseCase.findMailByCode(MailCode.REGISTER);
        // 템플릿 없다면 발송 x
        if (ObjectUtils.isEmpty(mail)) {
            return;
        }
        String content = mail.getContent();
        User user = event.getUser();
        List<String> fields = SuperClassReflectionUtils.getAllFields(User.class).stream().map(Field::getName).collect(Collectors.toList());
        List<String> methods = SuperClassReflectionUtils.getOnlyClassMethods(User.class).stream().map(Method::getName).collect(Collectors.toList());
        Class<?> classObj = user.getClass();
        for (String field : fields) {
            String old = "${" + field + "}";
            if (content.contains(old)) {
                for (String method : methods) {
                    if (method.toLowerCase(Locale.ROOT).equals(("get" + field).toLowerCase(Locale.ROOT))) {
                        try {
                            Method declaredMethod = classObj.getDeclaredMethod(method);
                            String value = (String)declaredMethod.invoke(user);
                            content = content.replace(old, value);
                        } catch (Exception e) {
                            log.error("[AUTO_MAILING_ERROR] {}", e.getMessage());
                            throw new CustomException(ErrorCode.AUTO_MAIL_ERROR.getMessage(), ErrorCode.AUTO_MAIL_ERROR);
                        }
                    }
                }
            }
        }
        log.info("[AUTO_MAILING_BODY] {}", content);
        MailDto mailDto = MailDto.builder().subject(mail.getSubject()).receiver(user.getEmail()).messageBody(content).build();
        emailUseCase.sendMailByHTML(mailDto);
    }

}

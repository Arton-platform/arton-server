package com.arton.backend.infra.event;

import com.arton.backend.infra.utils.SuperClassReflectionUtils;
import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
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

    /**
     * 회원가입 메일 처리
     * content ex: ${nickname}님 회원 가입을 축하드려요~
     * @param event
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = UserRegisteredEvent.class)
    void handle(UserRegisteredEvent event) {
        // 우선 회원가입 템플릿이 있나 확인
        Mail mail = mailUseCase.findMailByCode(MailCode.REGISTER);
        // 템플릿이 없다면 발송 x
        if (ObjectUtils.isEmpty(mail)) {
            return;
        }
        // 템플릿 존재시 해당 템플릿의 지정 필드의 값을 replace 해주자.
        String content = mail.getContent();
        User user = event.getUser();
        // map key field name value methodName
        HashMap<String, String> db = new HashMap<>();
        // get field name
        List<String> fields = SuperClassReflectionUtils.getAllFields(User.class).stream().map(Field::getName).collect(Collectors.toList());
        // get method name
        List<String> methods = SuperClassReflectionUtils.getAllMethods(User.class).stream().map(Method::getName).collect(Collectors.toList());
        //
        for (String field : fields) {
            String old = "${" + field + "}";

        }


        MailDto mailDto = MailDto.builder().subject(mail.getSubject()).receiver(user.getEmail()).build();


    }

}

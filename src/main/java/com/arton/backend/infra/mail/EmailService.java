package com.arton.backend.infra.mail;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailUseCase{
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Async
    @Override
    public void sendMailOnlyText(MailDto details) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(details.getReceiver());
            simpleMailMessage.setText(details.getMessageBody());
            simpleMailMessage.setSubject(details.getSubject());
            // send
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.MAIL_SEND_ERROR.getMessage(), ErrorCode.MAIL_SEND_ERROR);
        }

    }

    @Async
    @Override
    public void sendMailWithAttachment(MailDto details) {
        return ;
    }
}

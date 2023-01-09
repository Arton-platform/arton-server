package com.arton.backend.infra.mail;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailUseCase{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;
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
    public void sendMailByHTML(MailDto details) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(details.getReceiver());
            helper.setText(setContext(details.getMessageBody(), "password"), true); // html type
            helper.setSubject(details.getSubject());
            //send
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.MAIL_SEND_ERROR.getMessage(), ErrorCode.MAIL_SEND_ERROR);
        }
    }

    /**
     * @param value set value
     * @param type is html repo
     * @return
     */
    private String setContext(String value, String type) {
        Context context = new Context();
        context.setVariable("password", value);
        return springTemplateEngine.process(type, context);
    }

    @Async
    @Override
    public void sendMailWithAttachment(MailDto details) {
        return ;
    }
}

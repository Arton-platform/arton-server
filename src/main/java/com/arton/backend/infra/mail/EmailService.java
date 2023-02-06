package com.arton.backend.infra.mail;

import com.amazonaws.services.s3.AmazonS3Client;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
    @Autowired
    private FileUploadUtils fileUploadUtils;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.password-mail-key}")
    private String mailKey;

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

    /**
     * 메일이 발송 될 때 까지 기다리는건 메일 서버 상황에 따라 시간이 오래 걸릴 수 있기 때문에 비동기 처리.
     * @param details
     */
    @Async
    @Override
    public void sendMailByHTML(MailDto details) {
        try {
            // get mail form
            String fileContent = fileUploadUtils.getFileContent(mailKey);
            // input password
            String mailBody = fileContent.replace("${password}", details.getMessageBody());
            // send mail
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(details.getReceiver());
            helper.setText(mailBody, true); // html type
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

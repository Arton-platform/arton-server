package com.arton.backend.mail.application.service;

import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.data.MailMultiReceiversDto;
import com.arton.backend.mail.application.port.in.EmailUseCase;
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

/**
 * 메일을 직접 보내는 서비스 로직
 */
@Service
public class EmailService implements EmailUseCase {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private FileUploadUtils fileUploadUtils;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.passwordTemplate}")
    private String passwordTemplate;
    @Value("${spring.mail.commonTemplate}")
    private String commonTemplate;

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
            // get mail form
            String fileContent = fileUploadUtils.getFileContent(commonTemplate);
            // input message
            String mailBody = fileContent.replace("${content}", details.getMessageBody());
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
     * 메일이 발송 될 때 까지 기다리는건 메일 서버 상황에 따라 시간이 오래 걸릴 수 있기 때문에 비동기 처리.
     * @param details
     */
    @Async
    @Override
    public void sendPasswordMailByHTML(MailDto details) {
        try {
            // get mail form
            String fileContent = fileUploadUtils.getFileContent(passwordTemplate);
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
     * 여러 대상에게 메일 보내기
     * @param details
     */
    @Async
    @Override
    public void sendMailWithMultipleReceivers(MailMultiReceiversDto details) {
        try {
            int sz = details.getReceivers().size();
            String[] receivers = details.getReceivers().toArray(new String[sz]);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(details.getReceivers().toArray(receivers));
            simpleMailMessage.setText(details.getMessageBody());
            simpleMailMessage.setSubject(details.getSubject());
            // send
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
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

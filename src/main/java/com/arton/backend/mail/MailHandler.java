package com.arton.backend.mail;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class MailHandler {
    private JavaMailSender javaMailSender;
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage message;

    public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        message = javaMailSender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setFrom(String from) throws MessagingException {
        mimeMessageHelper.setFrom(from);
    }

    public void setTo(String email) throws MessagingException {
        mimeMessageHelper.setTo(email);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    public void setText(String text, boolean useHtml) throws MessagingException {
        mimeMessageHelper.setText(text, useHtml);
    }

    public void setAttach(String displayFileName, MultipartFile file) throws MessagingException {
        mimeMessageHelper.addAttachment(displayFileName, file);
    }

    public void setInline(String contentId, MultipartFile file) throws MessagingException, IOException {
        mimeMessageHelper.addInline(contentId, new ByteArrayResource(file.getBytes()), "image/jpeg");
    }

    public void send() {
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

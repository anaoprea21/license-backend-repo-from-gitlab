package com.example.licenseebe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@RestController
@Log4j2
public class EmailController {

    public final JavaMailSender emailSender;

    @Autowired
    EmailController(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendSimpleEmail(String text, String subject, String[] toEmail, File file) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            helper.setFrom("ana.oprea1409@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text, true);
            if (file != null) {
                helper.addAttachment("logs.txt", file);
            }
            emailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
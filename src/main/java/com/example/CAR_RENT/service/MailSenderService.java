package com.example.CAR_RENT.service;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    public String EMAIL;

    @Autowired
    Configuration configuration;


    @Autowired
    public JavaMailSender javaMailSender;

    @Async
    public void send(String text, String href, String to) throws MailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("Регистрация аккаунта DiamondRent");
            helper.setTo(to);
            helper.setFrom(EMAIL);
            String emailContent;
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("message", text);
            model.put("href", href);
            configuration.setDirectoryForTemplateLoading(new File("/home/vladimir/Downloads/CAR_RENT/src/main/resources/templates"));
            configuration.getTemplate("/mail_template.ftlh").process(model, stringWriter);
            emailContent = stringWriter.getBuffer().toString();
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.CAR_RENT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    public String EMAIL;

    @Autowired
    public JavaMailSender javaMailSender;


    @Async
    public void send(String text, String to) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom(EMAIL);
        mail.setText(text);
        javaMailSender.send(mail);
    }
}

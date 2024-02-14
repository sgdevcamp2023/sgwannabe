package com.lalala.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String adminEMail;

    private final JavaMailSender mailSender;

    public void createMessageForm(String to, String subject, String text) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(adminEMail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    @Async
    public void sendSignUpMail(String email, String code) {
        try {
            createMessageForm(email, "[라라라] 회원가입 인증 이메일", "인증 코드: " + code);
        } catch (MailException e) {
            throw new BusinessException(ErrorCode.SEND_MAIL_ERROR);
        }
    }
}

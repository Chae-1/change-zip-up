package com.kosa.chanzipup.api.email.service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
    일반 회원 가입 시, 가입 email로 인증 메시지를 전달해줘야한다.
 */
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final MailSendClient mailSendClient;

//    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        mailSendClient.sendVerificationCode("coguddlf1000@naver.com", "oh,my, god");
    }

    public boolean sendAuthenticationCodeTo(String email, String verificationCode) {
        boolean result = mailSendClient.sendVerificationCode(email, verificationCode);
        return true;
    }


}

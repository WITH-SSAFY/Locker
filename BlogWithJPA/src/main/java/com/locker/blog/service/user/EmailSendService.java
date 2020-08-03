package com.locker.blog.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class EmailSendService {

    @Autowired private JavaMailSender javaMailSender;

    private void sendMail(String email, String sub, String text) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(email);
        simpleMessage.setSubject(sub);
        simpleMessage.setText(text);
        javaMailSender.send(simpleMessage);
    }

    public void sendVerificationMail(String email) {
        String VERIFICATION_LINK = "http://http://i3a606.p.ssafy.io/user/verify/";
        UUID uuid = UUID.randomUUID();
        sendMail(email,"[Locker] 블로그 회원가입 인증메일입니다.",VERIFICATION_LINK+uuid.toString());
    }
}
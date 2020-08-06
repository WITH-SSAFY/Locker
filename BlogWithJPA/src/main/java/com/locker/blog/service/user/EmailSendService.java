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
        StringBuilder VERIFICATION_LINK = new StringBuilder()
                .append("아래 링크를 클릭하시면 이메일 인증이 완료됩니다.").append("\n\n")
                .append("https://i3a606.p.ssafy.io/verify/success");

        sendMail(email,"[Locker] 블로그 회원가입 인증메일입니다.",VERIFICATION_LINK.toString());
    }
}
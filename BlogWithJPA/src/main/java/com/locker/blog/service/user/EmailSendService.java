package com.locker.blog.service.user;

import com.locker.blog.repository.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
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

    public String sendVerificationMail(String email) {
        String pin = excuteGenerate();

        String VERIFICATION_LINK = new StringBuilder()
                .append("이메일 인증 번호를 확인해주세요.").append("\n\n")
                .append(pin).toString();

        sendMail(email,"[Locker] 블로그 회원가입 인증메일입니다.",VERIFICATION_LINK);

        return pin;
    }
    public UUID sendFindPassword(String email) {
        UUID uuid = UUID.randomUUID();
        StringBuilder NEW_PASSWORD_LINK = new StringBuilder()
                .append("임시 비밀번호를 제공해드립니다. 로그인 후 비밀번호 변경을 권장합니다.").append("\n\n")
                .append(uuid);

        sendMail(email,"[Locker] 블로그 임시 비밀번호입니다.",NEW_PASSWORD_LINK.toString());

        return uuid;
    }

    private String excuteGenerate() {
        int certNumLength = 6;
        Random random = new Random(System.currentTimeMillis());

        int range = (int)Math.pow(10,certNumLength);
        int trim = (int)Math.pow(10, certNumLength-1);
        int result = random.nextInt(range)+trim;

        if(result>range){
            result = result - trim;
        }

        return String.valueOf(result);
    }
}
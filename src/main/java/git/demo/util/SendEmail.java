package git.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;


@Service
@RequiredArgsConstructor
public class SendEmail {

    private final JavaMailSender javaMailSender;


    public String sendMail(String subject, String email) throws MessagingException {

        String authNumber = getAuthNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
//        message.setText("인증번호는" + authNumber + "입니다");
        message.setContent("<a href=\"https://localhost:8080/member/finFindIdAndPw\">[비밀번호 변경하러 가기]</a> 인증번호는 " + authNumber + "입니다","text/html; charset=utf-8");

        javaMailSender.send(message);

        return authNumber;
    }

    private String getAuthNumber() {
        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            stringBuffer.append(secureRandom.nextInt(8));
        }
        return stringBuffer.toString();
    }


}

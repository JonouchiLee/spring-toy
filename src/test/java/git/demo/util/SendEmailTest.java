package git.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
class SendEmailTest {

    private static final String SUBJECT = "회원가입인증";

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private SendEmail sendEmail;


    @Test
    @DisplayName("이메일")
    void sendMailTest() throws MessagingException {
        sendEmail.sendMail(SUBJECT, "dudwls0505@naver.com");
    }
}
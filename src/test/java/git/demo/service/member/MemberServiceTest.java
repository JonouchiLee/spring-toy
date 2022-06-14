package git.demo.service.member;

import git.demo.domain.member.Member;
import git.demo.exception.DuplicateIdException;
import git.demo.mapper.MemberMapper;
import git.demo.util.PasswordEncrypt;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberMapper memberMapper;

    @MockBean
    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;




    Member member;

    @BeforeEach
    void setup() {
        member = new Member();
        member.setId(1L);
        member.setUserName("이영진");
        member.setUserId("testtest");
        member.setUserPw("dudwls0505");
        member.setUserEmail("dudwls0505@naver.com");
    }

    @Test
    @DisplayName("회원가입 정상테스트")
    @Order(1)
    void memberSaveTest() {
        memberService.save(member);
        assertThat(memberService.findByLoginId("testtest")).isEqualTo(member);
    }


    @Test
    @DisplayName("중복회원가입시 에러발생해야한다")
    @Order(2)
    void memberSaveExceptionTest() {
        assertThrows(DuplicateIdException.class, () ->{
            memberService.save(member);
        });
    }

//    @Test
//    @DisplayName("회원 삭제")
//    @Order(3)
//    void clear() {
//        memberMapper.deleteMember(1L);
//        assertNull(memberService.findByLoginId("test"));
//    }


    @Test
    @DisplayName("비밀번호 변경 테스트")
    @Order(3)
    void newPasswordTest() {
        memberService.update("testtest","dudwls1234");
        assertThat(PasswordEncrypt.isMatch("dudwls1234",memberMapper.findPwByUserId("testtest"))).isTrue();
    }
}
package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.domain.member.ResetPasswordForm;
import git.demo.domain.member.SetUserIdAndMailAuthNum;
import git.demo.exception.DifferentPasswordException;
import git.demo.mapper.BookMapper;
import git.demo.mapper.MemberMapper;
import git.demo.service.book.BookService;
import git.demo.service.member.LoginService;
import git.demo.service.member.MemberService;
import git.demo.util.SendEmail;
import git.demo.web.session.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private LoginService loginService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;

    @MockBean
    private SendEmail sendEmail;

    @MockBean
            private SetUserIdAndMailAuthNum setUserIdAndMailAuthNum;

    Member member;
    @MockBean
    ResetPasswordForm resetPasswordForm;

    @BeforeEach
    void sessionSetup() {
        member = new Member();
        member.setUserId("test");
        member.setId(1L);
        member.setUserPw("1234");


    }


    @Test
    @DisplayName("세션이있으면 로그인페이지로, 세션이없으면 index페이지로 간다")
    void homeLoginTest() throws Exception {

        /**
         * 세션이 있을때
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .sessionAttr("loginMember", member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, notNullValue()))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("member/loginON"));

        /**
         * 세션이 없을때
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, nullValue()))
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("loginMemberForm 테스트")
    void loginMemberFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"))
                .andExpect(model().attributeExists("loginForm"));
    }


    /**
     * chkResult때문에 안됨...나중에 해결하기
     */
    @Test
    @DisplayName("loginMember Bindresult가없으면 리다이렉트한다.")
    void loginMemberTest() throws Exception {
        Member chkResult = member;
        mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
                        .param("loginId", "test")
                        .param("loginPw", "1234"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("loginMember BindResult가있으면 login페이지로 간다")
    void loginMember_실패() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("member/login"));
    }

    /**
     * /logout 테스트
     * //
     */
//    @Test
//    @DisplayName("")



    /**
     * sendEmail 메소드 테스트
     */
//    @Test
//    @DisplayName("")
//    void sendEmailTest() {
//
//    }

    @Test
    @DisplayName("비밀번호 재설정페이지로 이동")
    void finSendmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member/finFindIdAndPw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("resetPasswordForm"))
                .andExpect(view().name("member/finFindIdAndPw"));
    }



}
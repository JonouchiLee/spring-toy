package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.domain.member.ResetPasswordForm;
import git.demo.domain.member.SetUserIdAndMailAuthNum;
import git.demo.mapper.BookMapper;
import git.demo.mapper.MemberMapper;
import git.demo.service.book.BookService;
import git.demo.service.member.LoginService;
import git.demo.service.member.MemberService;
import git.demo.util.SendEmail;
import git.demo.web.session.SessionConst;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberService memberService;

    Member member;


    @BeforeEach
    void sessionSetup() {
        memberMapper.deleteAll();

        member = new Member();
        member.setUserEmail("dudwls0505@naver.com");
        member.setUserId("testEx");
        member.setId(1L);
        member.setUserPw("juliet1225");
        member.setUserName("이영진");
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


    @Test
    @DisplayName("로그인 검증성공")
    void loginMemberTest() throws Exception {
        memberService.save(member);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
                        .param("loginId", "testEX")
                        .param("loginPw", "juliet1225"))
                .andDo(print())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, notNullValue()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("로그인 검증실패 : loginMember BindingResult가있으면 login페이지로 간다")
    void loginMember_fail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("member/login"));
    }

    /**
     * 로긴서비스에 예외처리 한후 테스트해보기
     */

//    @Test
//    @DisplayName("로그인 검증실패2: 존재하지않는 회원으로 로그인하면 login페이지로 간다")
//    void loginMember_fail2() throws Exception {
//        memberService.save(member);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
//                .param("loginId", "test12345")
//                .param("loginPw", "juliet4567"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(model().hasErrors())
//                .andExpect(view().name("member/login"));
//    }

    /**
     * /logout 테스트..
     * //
     */
//    @Test
//    @DisplayName("로그아웃 테스트: 세션있으면 해제시킨다")
//    void logout_ok() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/logout")
//                        .sessionAttr("loginMember", member))
//                .andDo(print())
//                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, notNullValue()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/"));
//    }



//    @Test
//    @DisplayName("비밀번호 재설정페이지로 이동")
//    void finSendmail() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/member/finFindIdAndPw"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("resetPasswordForm"))
//                .andExpect(view().name("member/finFindIdAndPw"));
//    }



}
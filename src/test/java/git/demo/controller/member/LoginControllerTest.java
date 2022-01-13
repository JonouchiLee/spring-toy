package git.demo.controller.member;

import git.demo.domain.login.LoginService;
import git.demo.repository.member.MemberRepository;
import git.demo.web.session.SessionConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("home GetMapping 테스트")
    void HomeGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER,notNullValue()));
    }

    @Test
    @DisplayName("login GetMapping 테스트")
    void LoginGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"))
                .andExpect(model().attributeExists("loginForm"));
    }


//    @Test
//    @DisplayName("login 중복회원 테스트")
//    void LoginCloneLoginFail() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/login"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(model().attributeHasErrors("loginFail"))
//                .andExpect(model().attributeHasFieldErrors("member", "userId"))
//                .andExpect(model().attributeHasFieldErrors("member", "userPw"))
//                .andExpect(view().name("member/join"));
//
//    }

//    @Test
//    @DisplayName("로그인 성공 테스트")
//    void joinPostTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
//    }

//    @Test
//    @DisplayName("로그인 실패 테스트")
//    void joinPostHasErrors() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/join"))
//    }

}
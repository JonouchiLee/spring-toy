package git.demo.controller.member;

import git.demo.mapper.BookMapper;
import git.demo.mapper.MemberMapper;
import git.demo.service.book.BookService;
import git.demo.service.member.LoginService;
import git.demo.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class MemberControllerTest {

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

    @Test
    @DisplayName("회원가입폼으로 포워딩한다")
    void joinMemberFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("member"));
    }

    @Test
    void joinPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
                .param("userId" , "YoungJin")
                .param("userPw", "juliet12"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    /**
     * 바인딩리절트관련 테스트 추가해야된다.
     */
//    @Test
//    void joinPostTest_실패() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
//                .param("userId" , "YoungJin")
//                .param("userPw", "juliet12"));
//
//
//        ResultActions perform2 = mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
//                .param("userId", "YoungJin")
//                .param("userPw", "juliet12"));
//
//        perform2.andExpect(view().name("member/join"));
//
//
//    }


//    @Test
//    void joinPostHasErrors() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/join"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(model().attributeHasErrors("member"))
//                .andExpect(model().attributeHasFieldErrors("member", "userId"))
//                .andExpect(model().attributeHasFieldErrors("member", "userPw"))
//                .andExpect(view().name("member/join"));
//    }



}
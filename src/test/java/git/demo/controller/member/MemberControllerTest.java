package git.demo.controller.member;

import git.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    void joinGetTest() throws Exception {
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

    @Test
    void joinPostHasErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("member"))
                .andExpect(model().attributeHasFieldErrors("member", "userId"))
                .andExpect(model().attributeHasFieldErrors("member", "userPw"))
                .andExpect(view().name("member/join"));
    }



}
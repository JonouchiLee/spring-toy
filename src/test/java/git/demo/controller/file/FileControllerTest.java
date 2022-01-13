package git.demo.controller.file;


import git.demo.domain.login.LoginService;
import git.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LoginService loginService;
    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("서블릿 GetMapping 테스트")
    void fileTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/upload"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("file/upload"));
    }


}
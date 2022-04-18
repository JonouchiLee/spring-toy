package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.domain.member.SetUserIdAndMailAuthNum;
import git.demo.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    private SetUserIdAndMailAuthNum setUserIdAndMailAuthNum;


    @BeforeEach
    void setup() {
        memberMapper.deleteAll();
    }

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
    @DisplayName("회원가입 검증에 성공시 리다이렉트한다")
    void joinPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
                        .param("userName", "김민석")
                        .param("userId", "kimninsuck")
                        .param("userPw", "dudwls0505")
                        .param("userEmail", "dudwls0505@nate.com"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }


//    @Test
//    @DisplayName("회원가입폼 검증 실패케이스 1. 중복회원 ")
//    void joinForm() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
//                .param("userName", "김민석")
//                .param("userId", "kimninsuck2")
//                .param("userPw", "dudwls0505")
//                .param("userEmail", "dudwls0505@nate.com"));
//
//        ResultActions perform2 = mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
//                .param("userName", "이영진")
//                .param("userId", "kimninsuck2")
//                .param("userPw", "dudwls05055")
//                .param("userEmail", "dudwls0505@natee.com"));
//
//        perform2.andExpect(view().name("member/join"));
//    }

    @Test
    @DisplayName("회원가입폼 검증 실패케이스 2. BindingResult ")
    void joinFormFail_BindingError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
                .param("userName", "김민석")
                .param("userId", "kimninsuck2")
                .param("userEmail","dudwls0505@gmail.com"))
                .andDo(print())
                .andExpect(model().hasErrors());

    }


    /**
     * 이메일 중복확인 테스트 해보기
     */


    @Test
    @DisplayName("아이디찾기페이지 테스트")
    void FindIdPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member/findIdAndPw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("findIdForm"))
                .andExpect(view().name("member/findIdAndPw"));
    }

    @Test
    @DisplayName("아이디찾기폼 검증성공 ")
    void FindIdForm_Success() throws Exception {
        memberMapper.insertMember(new Member(1L,"김민석","testEx","dudwls0505!","dudwls0505@nate.com"));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/findIdAndPw")
                        .param("findIdUserName", "김민석")
                        .param("findIdUserEmail", "dudwls0505@nate.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("resetPasswordForm"))
                .andExpect(view().name("member/finFindIdAndPw"));

//        then(javaMailSender).should().send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("아이디찾기폼 실패테스트 ")
    void findIdForm_Fail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/findIdAndPw"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("findIdForm", "findIdUserName"))
                .andExpect(model().attributeHasFieldErrors("findIdForm", "findIdUserEmail"))
                .andExpect(view().name("member/findIdAndPw"));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/findIdAndPw")
                        .param("findIdUserName", "이영진"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("findIdForm", "findIdUserEmail"))
                .andExpect(view().name("member/findIdAndPw"));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/findIdAndPw")
                        .param("findIdUserEmail", "dudwls0505@nate.com"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("findIdForm", "findIdUserName"))
                .andExpect(view().name("member/findIdAndPw"));
    }

    /**
     * hasErrors : Model에 에러가있는지
     * attributeHasErrors : attribute에 에러가있냐없냐
     * attributeHasFieldErrors: 필드에 에러가있는지 확인
     */


//    @Test
//    @DisplayName("비밀번호 재설정폼페이지 테스트")
//    void pwResetPage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/member/finFindIdAndPw"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("member/finFindIdAndPw"));
//    }

    @Test
    @DisplayName("비밀번호 재설정폼페이지 검증성공시")
    void findSendEmailPost() throws Exception {
        setUserIdAndMailAuthNum.setMailAuthNumber("1234");
        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("userId", "testEx")
                .param("newPassword","dudwls1234")
                .param("newPasswordCheck","dudwls1234")
                .param("mailAuthNumber","1234"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    @DisplayName("비밀번호 재설정폼페이지 검증실패테스트 1. bindingResult.hasErrors")
    void findSendEmailPost_BindingException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("newPassword","dudwls1234")
                        .param("newPasswordCheck","dudwls1234"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("resetPasswordForm","mailAuthNumber"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/finFindIdAndPw"));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("newPassword","dudwls1234")
                        .param("mailAuthNumber","1234"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("resetPasswordForm","newPasswordCheck"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/finFindIdAndPw"));

        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("newPasswordCheck","dudwls1234")
                        .param("mailAuthNumber","1234"))
                .andDo(print())
                .andExpect(model().attributeHasFieldErrors("resetPasswordForm","newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/finFindIdAndPw"));
    }

    @Test
    @DisplayName("비밀번호 재설정폼페이지 검증실패테스트 2.비밀번호 불일치")
    void findSendEmailPost_pwNotMatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("newPassword", "dudwls1234")
                        .param("newPasswordCheck", "dudwls123")
                        .param("mailAuthNumber","123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/finFindIdAndPw"));
    }

    @Test
    @DisplayName("비밀번호 재설정폼페이지 검증실패테스트 3.이메일 인증번호 불일치")
    void findSendEmailPost_emailAuthNotMatch() throws Exception {
        setUserIdAndMailAuthNum.setMailAuthNumber("5678");

        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
                        .param("newPassword", "dudwls123")
                        .param("newPasswordCheck", "dudwls123")
                        .param("mailAuthNumber", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/finFindIdAndPw"));
    }



    



//    @Test
//    @DisplayName("비밀번호 재설정페이지 검증성공")
//    void pwResetForm_Success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/member/finFindIdAndPw")
//                .param(""))
//    }
}
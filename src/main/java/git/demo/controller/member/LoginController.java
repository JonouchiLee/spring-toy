package git.demo.controller.member;

import git.demo.domain.Member;
import git.demo.domain.login.LoginForm;
import git.demo.domain.login.LoginService;
import git.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @GetMapping("member/login")
    public String loginMemberForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        log.info("GetMapping login 실행");
        return "member/login";
    }

    @PostMapping("member/login")
    public String loginMember(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/login";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());
        log.info("login 계정 {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "member/login";
        }
        
        return "redirect:/";
    }

}

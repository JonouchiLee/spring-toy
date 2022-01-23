package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.domain.member.login.LoginForm;
import git.demo.service.login.LoginService;
import git.demo.repository.member.MemberRepository;
import git.demo.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            System.out.println("loginMember==null");
            return "index";
        }
        model.addAttribute("member", loginMember);
        return "member/loginON";
    }


    @GetMapping("member/login")
    public String loginMemberForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        log.info("GetMapping login 실행");
        return "member/login";
    }

    @PostMapping("member/login")
    public String loginMember(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/login";
        }

//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());
        Member chkResult = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());
        log.info("login 계정 {}", chkResult);

        if (chkResult == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "member/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, chkResult);


        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

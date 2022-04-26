package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.domain.member.login.LoginForm;
import git.demo.domain.study.Study;
import git.demo.service.member.LoginService;
import git.demo.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        model.addAttribute("study", new Study());

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
        return "member/login";
    }

    @PostMapping("member/login")
    public String loginMember(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/login";
        }

        Member chkResult = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());

        if (chkResult == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "member/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, chkResult);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}

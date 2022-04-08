package git.demo.controller.member;

import git.demo.domain.FindIdForm;
import git.demo.domain.member.Member;
import git.demo.domain.member.ResetPasswordForm;
import git.demo.domain.member.SetUserIdAndMailAuthNum;
import git.demo.domain.member.login.LoginForm;
import git.demo.exception.DifferentPasswordException;
import git.demo.service.member.LoginService;
import git.demo.service.member.MemberService;
import git.demo.util.SendEmail;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final SendEmail sendEmail;
    private final SetUserIdAndMailAuthNum setUseridAndMailAuthnum;


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

    @GetMapping("/logout")
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/member/findIdAndPw")
    public String forwardFindId(Model model) {
        model.addAttribute("findIdForm", new FindIdForm());
        return "member/findIdAndPw";
    }

    @PostMapping("/member/findIdAndPw")
    public String sendEmail(@Valid @ModelAttribute FindIdForm findIdForm, Model model) {
        try {
            String getEmail = memberService.findEmailByName(findIdForm.getFindIdUserName(), findIdForm.getFindIdUserEmail());
            String setMailAuthNumber = sendEmail.sendMail("회원가입인증", getEmail);
            setUseridAndMailAuthnum.setMailAuthNumber(setMailAuthNumber);
            //이름이나 이메일로 유저id알아오기..
            setUseridAndMailAuthnum.setUserId(memberService.findUserIdByEmail(findIdForm.getFindIdUserEmail()));
//            model.addAttribute("email", setMailAuthNumber);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "member/finFindIdAndPw";
    }

    @GetMapping("/member/finFindIdAndPw")
    public String finSendEmail(Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        return "member/finFindIdAndPw";
    }

    @PostMapping("/member/finFindIdAndPw")
    public String findSendEmailPost(@ModelAttribute ResetPasswordForm resetPasswordForm) {

        if (!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getNewPasswordCheck())) {
            throw new RuntimeException("비밀번호가 일치하지않습니다.");
        }

        if (resetPasswordForm.getMailAuthNumber().equals(setUseridAndMailAuthnum.getMailAuthNumber())) {
            resetPasswordForm.setMailAuthNumber(setUseridAndMailAuthnum.getMailAuthNumber());
        }
        return "redirect:/";
    }
}

package git.demo.controller.member;

import git.demo.controller.validator.FindMemberFormValidator;
import git.demo.controller.validator.JoinMemberFormValidator;
import git.demo.controller.validator.ResetPasswordFormValidator;
import git.demo.domain.FindIdForm;
import git.demo.domain.member.Member;
import git.demo.domain.member.ResetPasswordForm;
import git.demo.domain.member.SetUserIdAndMailAuthNum;
import git.demo.mapper.MemberMapper;
import git.demo.service.member.MemberService;
import git.demo.test.MockAddress;
import git.demo.util.SendEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SendEmail sendEmail;
    private final MemberMapper memberMapper;
    private final SetUserIdAndMailAuthNum setUseridAndMailAuthnum;
    private final JoinMemberFormValidator joinMemberFormValidator;
    private final FindMemberFormValidator findMemberFormValidator;
    private final ResetPasswordFormValidator resetPasswordFormValidator;

    @InitBinder("member")
    public void initJoinMemberForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinMemberFormValidator);
    }

    @InitBinder("findIdForm")
    public void initFindMemberForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(findMemberFormValidator);
    }

    @InitBinder("resetPasswordForm")
    public void initResetPasswordFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(resetPasswordFormValidator);
    }

    @GetMapping("member/join")
    public String joinMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/join";
    }

    @PostMapping("member/join")
    public String joinMember(@Valid @ModelAttribute Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/join";
        }

        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("member/findIdAndPw")
    public String forwardFindId(Model model) {
        model.addAttribute("findIdForm", new FindIdForm());
        return "member/findIdAndPw";
    }

    @PostMapping("member/findIdAndPw")
    public String sendEmail(@Valid @ModelAttribute FindIdForm findIdForm,BindingResult bindingResult, Model model) {

        // 검증실패
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/findIdAndPw";
        }
        // 검증성공
        String getEmail = null;
        try {
            getEmail = memberService.findEmailByName(findIdForm.getFindIdUserName(), findIdForm.getFindIdUserEmail());
            String setMailAuthNumber = sendEmail.sendMail("회원가입인증", getEmail);
            setUseridAndMailAuthnum.setMailAuthNumber(setMailAuthNumber);
            //이름이나 이메일로 유저id알아오기..
            setUseridAndMailAuthnum.setUserId(memberService.findUserIdByEmail(findIdForm.getFindIdUserEmail()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setUserId(memberMapper.findUseridByEmail(getEmail));
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "member/finFindIdAndPw";
    }

    @GetMapping("member/finFindIdAndPw")
    public String finSendEmail(Model model) {
        return "member/finFindIdAndPw";
    }

    @PostMapping("member/finFindIdAndPw")
    public String findSendEmailPost(@Valid @ModelAttribute ResetPasswordForm resetPasswordForm,BindingResult bindingResult, Model model) {

        //검증실패시
        if (bindingResult.hasErrors()) {
            return "member/finFindIdAndPw";
        }

        //검증실패시2 : 비밀번호 불일치
        if (!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getNewPasswordCheck())) {
            return "member/finFindIdAndPw";
        }

        //검증실패시3: 이메일인증번호 불일치시
        if (!resetPasswordForm.getMailAuthNumber().equals(setUseridAndMailAuthnum.getMailAuthNumber())) {
            return "member/finFindIdAndPw";
        }
        return "redirect:/";
    }


}

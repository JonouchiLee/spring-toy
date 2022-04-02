package git.demo.controller.member;

import git.demo.domain.member.Member;
import git.demo.mapper.MemberMapper;
import git.demo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("member/join")
    public String joinMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/join";
    }

    @PostMapping("member/join")
    public String joinMember(@Valid @ModelAttribute Member member , BindingResult bindingResult) {

        boolean CheckCloneMember = memberMapper.isExistsId(member.getUserId());

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "member/join";
        }

        else if(CheckCloneMember) {
            log.info("Clone check errors={}", bindingResult);
            bindingResult.reject("joinFailClone", "이미 존재하는 아이디입니다.");
            return "member/join";
        }
        memberService.save(member);
        return "redirect:/";
    }



}

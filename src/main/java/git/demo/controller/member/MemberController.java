package git.demo.controller.member;

import git.demo.domain.Member;
import git.demo.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    @GetMapping("member/join")
    public String joinMemberForm(Model model) {
        model.addAttribute("member", new Member());
        log.info("GetMapping join 실행");
        return "member/join";
    }

    @PostMapping("member/join")
    public String joinMember(@Valid @ModelAttribute Member member , BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "member/join";
        }

        memberRepository.save(member);
        return "redirect:/";
    }



}

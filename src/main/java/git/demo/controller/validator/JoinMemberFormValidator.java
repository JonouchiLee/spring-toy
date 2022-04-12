package git.demo.controller.validator;

import git.demo.domain.member.Member;
import git.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinMemberFormValidator implements Validator {

    private final MemberMapper memberMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        if (memberMapper.isExistsId(member.getUserId())) {
            errors.rejectValue("userId","invalid.userId",new Object[]{member.getUserId()},"이미 존재하는 아이디입니다.");
        }

    }
}

package git.demo.service.member;

import git.demo.domain.member.Member;
import git.demo.mapper.MemberMapper;
import git.demo.util.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

    public Member login(String loginId, String loginPw) {
        Member memberLogin = memberMapper.findMemberById(loginId);
        if(PasswordEncrypt.isMatch(loginPw, memberLogin.getUserPw())) {
            return memberLogin;
        }
        return null;
    }

}

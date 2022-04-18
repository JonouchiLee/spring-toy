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

        System.out.println("로긴아디 =" +loginId);
        System.out.println("로긴비번 =" + loginPw);
        Member memberLogin = memberMapper.findMemberById(loginId);
        /**
         * 로그인 불일치시 에러 추가해야함
         */
        System.out.println("memberlogin = " + memberLogin);
        if(PasswordEncrypt.isMatch(loginPw, memberLogin.getUserPw())) {
            return memberLogin;
        }

        return null;
    }

}

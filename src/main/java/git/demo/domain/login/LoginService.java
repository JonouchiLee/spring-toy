package git.demo.domain.login;

import git.demo.domain.Member;
import git.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

//    public Member login(String loginId, String loginPw) {
//        return memberRepository.findByLoginId(loginId)
//                .filter(m -> m.getUserPw().equals(loginPw))
//                .orElse(null);
//    }

    public Member login(String loginId, String loginPw) {
        Member memberLoginId = memberMapper.findLoginId(loginId, loginPw);
        System.out.println("memberLoginId =" + memberLoginId);
        return memberLoginId;
    }


}

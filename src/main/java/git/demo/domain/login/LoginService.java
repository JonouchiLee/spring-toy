package git.demo.domain.login;

import git.demo.domain.Member;
import git.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String loginPw) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getUserPw().equals(loginPw))
                .orElse(null);
    }

}

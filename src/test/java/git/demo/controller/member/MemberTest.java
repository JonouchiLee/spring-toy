package git.demo.controller.member;

import git.demo.domain.Member;
import git.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private MemberRepository memberRepository = new MemberRepository();

    @Test
    void memberTest() {
        Member member = new Member();
        member.setId(1L);
        member.setUserId("tempId");
        member.setUserPw("tempPw");
        Member savedMember = memberRepository.save(member);
        assertThat(savedMember).isEqualTo(member);
    }

}

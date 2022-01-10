package git.demo.controller.member;

import git.demo.domain.Member;
import git.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private MemberRepository memberRepository = new MemberRepository();

    @Test
    @DisplayName("MemberRepository save() 테스트")
    void memberSaveTest() {
        Member member = new Member();
        member.setId(1L);
        member.setUserId("tempId");
        member.setUserPw("tempPw");
        Member savedMember = memberRepository.save(member);
        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    @DisplayName("MemberRepository findAll() 테스트")
    void memberFindAllTest(){
        ArrayList<Member> members = new ArrayList<>();
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        members.add(member1);
        members.add(member2);
        members.add(member3);
        assertThat(memberRepository.findAll()).isEqualTo(members);

    }



}

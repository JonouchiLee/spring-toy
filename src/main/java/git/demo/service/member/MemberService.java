package git.demo.service.member;


import git.demo.domain.member.Member;
import git.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberService {

    private static long sequence = 0L;

    private final MemberMapper memberMapper;


    public void save(Member member) {
        member.setId(++sequence);
        System.out.println("member상태="+  member + "member id값의상태=" + member.getId() );
        log.info("회원가입완료", member);
        memberMapper.insertMember(member);
    }


//    public List<Member> findAll() {
//        return new ArrayList<>(store.values());
//    }

//    public void findAll() {
//         memberMapper.selectMember();
//    }


    public Member findByLoginId(String loginId, String loginPw) {
        return memberMapper.findLoginId(loginId,loginPw);
    }

}

package git.demo.repository.member;


import git.demo.domain.Member;
import git.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    private final MemberMapper memberMapper;

//
//    public Member save(Member member) {
//        member.setId(++sequence);
//        log.info("회원가입완료", member);
//        store.put(member.getId(), member);
//        return member;
//    }

    public void save(Member member) {
        member.setId(++sequence);
        System.out.println("member상태="+  member + "member id값의상태=" + member.getId() );
        log.info("회원가입완료", member);
        memberMapper.insertMember(member);
    }


    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m-> m.getUserId().equals(loginId))
                .findFirst();
    }

}

package git.demo.repository.member;


import git.demo.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("회원가입완료", member);
        store.put(member.getId(), member);
        return member;
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

package git.demo.repository;


import git.demo.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();

    public Member save(Member member) {
        log.info("회원가입완료", member);
        store.put(member.getId(), member);
        return member;
    }


}

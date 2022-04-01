package git.demo.mapper;

import git.demo.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper

public interface MemberMapper {

    boolean isExistsId(String userId);

    void insertMember(Member member);

    Member findMemberById(String loginId);
}

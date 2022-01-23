package git.demo.mapper;

import git.demo.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {

    void insertMember(Member member);

    Member findLoginId(@Param("loginId")String loginId, @Param("loginPw")String loginPw);

    Member chkDuplicateId(String userId);

}

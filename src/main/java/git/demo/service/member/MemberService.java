package git.demo.service.member;


import git.demo.exception.DuplicateIdException;
import git.demo.domain.member.Member;
import git.demo.mapper.MemberMapper;
import git.demo.util.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;


    public Member save(Member member)  {
//        if(isExistsId(member.getUserId())){
//            throw new DuplicateIdException("이미 존재하는 아이디입니다."+ member.getUserId());
//        }
        encryptMember(member);
        System.out.println("MemberService로 넘어오는 member" + member);
        memberMapper.insertMember(member);
        return member;
    }

    public boolean isExistsId(String userId) {
        return memberMapper.isExistsId(userId);
    }
    public Member findByLoginId(String loginId) {
        return memberMapper.findMemberById(loginId);
    }


    public void DeleteMember(Long id) {
        memberMapper.deleteMember(id);
    }


    public void encryptMember(Member member) {
        String encryptedPassword = PasswordEncrypt.encrypt(member.getUserPw());
        member.setUserPw(encryptedPassword);
    }

    public String findEmailByName(String findIdUserName, String getUserEmail) {
        return memberMapper.findEmailByName(findIdUserName,getUserEmail);
    }

    public String findUserIdByEmail(String email) {
        return memberMapper.findUseridByEmail(email);
    }
}

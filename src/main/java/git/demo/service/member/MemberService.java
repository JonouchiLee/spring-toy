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

    private static long sequence = 0L;

    private final MemberMapper memberMapper;


    public Member save(Member member)  {
        if(isExistsId(member.getUserId())){
            throw new DuplicateIdException("이미 존재하는 아이디입니다."+ member.getUserId());
        }
        member.setId(++sequence);
        System.out.println("member상태="+  member + "member id값의상태=" + member.getId() );
        log.info("회원가입완료", member);
        encryptMember(member);
        memberMapper.insertMember(member);
        return member;
    }

    public boolean isExistsId(String userId) {
        return memberMapper.isExistsId(userId);
    }


//    public List<Member> findAll() {
//        return new ArrayList<>(store.values());
//    }

//    public void findAll() {
//         memberMapper.selectMember();
//    }


    public Member findByLoginId(String loginId) {
        return memberMapper.findMemberById(loginId);
    }



    public void encryptMember(Member member) {
        String encryptedPassword = PasswordEncrypt.encrypt(member.getUserPw());
        member.setUserPw(encryptedPassword);
    }

}

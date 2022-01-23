package git.demo.domain.member;


import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String userId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String userPw;
}

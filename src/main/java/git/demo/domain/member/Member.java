package git.demo.domain.member;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Member {

    private Long id;

    @Pattern(regexp = "^[가-힣|a-zA-Z]+$" ,message = "이름을 다시한번 확인해주세요")
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 1, max = 50, message = "이름은 최대 50자까지 입력해주세요")
    private String userName;

    @Pattern(regexp= "^[a-zA-Z0-9]+$",message = "아이디를 영문+숫자조합으로 입력해주세요")
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 6, max = 30, message = "아이디는 최소 6자 최대 30자까지 입력가능합니다.")
    private String userId;

    @Pattern(regexp ="^(?=.*[a-zA-Z]{1,20})(?=.*[0-9])(?=.*[~!@#$%^&*()-=+]).{8,20}$",message = "영문은 필수로 포함해주세요")
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 20, message = "비밀번호는 최소8자 최대 20자까지 입력가능합니다.")
    private String userPw;

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    @Size(max=50, message = "이메일을 적절히 입력해주세요")
    private String userEmail;

    public Member() {
    }

    public Member(Long id, String userName, String userId, String userPw, String userEmail) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
    }
}

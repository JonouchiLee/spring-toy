package git.demo.domain.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPw;

}
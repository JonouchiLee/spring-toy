package git.demo.domain.member;


import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class ResetPasswordForm {

    private String userId;
//    @NotEmpty
    private String newPassword;
//    @NotEmpty
    private String newPasswordCheck;
//    @NotEmpty
    private String mailAuthNumber;
}

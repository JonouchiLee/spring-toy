package git.demo.domain.member;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ResetPasswordForm {


    @NotBlank
    private String userId;

    @Pattern(regexp ="^(?=.*[a-zA-Z]{1,20})(?=.*[0-9])(?=.*[~!@#$%^&*()-=+]).{8,20}$",message = "영문은 필수로 포함해주세요")
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 20, message = "비밀번호는 최소8자 최대 20자까지 입력가능합니다.")
    private String newPassword;

    @Pattern(regexp ="^(?=.*[a-zA-Z]{1,20})(?=.*[0-9])(?=.*[~!@#$%^&*()-=+]).{8,20}$",message = "영문은 필수로 포함해주세요")
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 20, message = "비밀번호는 최소8자 최대 20자까지 입력가능합니다.")
    private String newPasswordCheck;

    @NotBlank(message = "인증번호를 입력해주세요")
    private String mailAuthNumber;
}

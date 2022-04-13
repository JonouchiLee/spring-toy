package git.demo.domain;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class FindIdForm {

    @Pattern(regexp = "^[가-힣|a-zA-Z]+$" ,message = "이름을 다시한번 확인해주세요")
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 1, max = 50, message = "이름은 최대 50자까지 입력해주세요")
    private String findIdUserName;
    @Email(message = "이메일 형식이 올바르지않습니다.")
    @NotBlank(message = "이메일을 입력해주세요")
    @Size(max=50, message = "이메일을 적절히 입력해주세요")
    private String findIdUserEmail;


}

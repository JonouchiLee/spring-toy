package git.demo.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindIdForm {

//    @NotEmpty
    private String findIdUserName;
//    @NotEmpty
    private String findIdUserEmail;


}

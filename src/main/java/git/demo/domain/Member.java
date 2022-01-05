package git.demo.domain;

import javax.validation.constraints.NotNull;

public class Member {

    @NotNull
    private String userId;
    @NotNull
    private String userPw;
}

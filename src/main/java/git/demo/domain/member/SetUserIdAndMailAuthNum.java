package git.demo.domain.member;

import lombok.Data;
import org.springframework.stereotype.Repository;


@Repository
@Data
public class SetUserIdAndMailAuthNum {

    private String userId;
    private String mailAuthNumber;
}

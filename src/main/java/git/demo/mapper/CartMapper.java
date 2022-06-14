package git.demo.mapper;

import git.demo.domain.study.Study;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

    void insertCart(Study study);
}

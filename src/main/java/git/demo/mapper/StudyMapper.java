package git.demo.mapper;

import git.demo.domain.study.Study;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyMapper {

    void insertStudy(Study study);
    Study findStudyNameById(Long id);
}

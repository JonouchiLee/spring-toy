package git.demo.service.study;


import git.demo.domain.study.Study;
import git.demo.mapper.StudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyMapper studyMapper;

    public Study add(Study study) {
        studyMapper.insertStudy(study);
        return study;
    }
//
//    public Study findStudyById(Long id) {
//        return studyMapper.findStudyById(id);
//    }
}

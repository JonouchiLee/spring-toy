package git.demo.service.cart;

import git.demo.domain.study.Study;
import git.demo.mapper.CartMapper;
import git.demo.mapper.StudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final StudyMapper studyMapper;

    public void addCart(Long id) {

        Study studyNameById = studyMapper.findStudyNameById(id);
        cartMapper.insertCart(studyNameById);
    }
}

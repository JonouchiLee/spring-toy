package git.demo.controller.validator;

import git.demo.domain.FindIdForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FindMemberFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FindIdForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FindIdForm findIdForm = (FindIdForm) target;

        if (findIdForm.getFindIdUserName() == null) {
            errors.rejectValue("findIdUserName","notnull","이름을 입력해주세요");
        }

        if (findIdForm.getFindIdUserEmail() == null) {
            errors.rejectValue("findIdUserEmail","notnull","이메일을 입력해주세요");
        }
    }
}

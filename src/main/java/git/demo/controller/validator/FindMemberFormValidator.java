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

    }
}

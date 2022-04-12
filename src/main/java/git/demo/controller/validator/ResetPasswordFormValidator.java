package git.demo.controller.validator;

import git.demo.domain.member.ResetPasswordForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResetPasswordForm resetPasswordForm = (ResetPasswordForm) target;

    }
}

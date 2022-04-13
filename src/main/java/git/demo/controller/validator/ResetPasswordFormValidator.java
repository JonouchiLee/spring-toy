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

        if (resetPasswordForm.getNewPassword() == null) {
            errors.rejectValue("newPassword","invalid.newPw","비밀번호를 입력해주세요");
        }


        if (resetPasswordForm.getNewPasswordCheck() == null) {
            errors.rejectValue("newPasswordCheck","invalid.newPwChk","비밀번호를 확인해주세요");
        }

        if (resetPasswordForm.getMailAuthNumber() == null) {
            errors.rejectValue("mailAuthNumber","invalid.mailAuthNum","메일인증번호를 입력해주세요");
        }

    }
}

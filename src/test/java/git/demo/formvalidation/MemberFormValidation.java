package git.demo.formvalidation;

import git.demo.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MemberFormValidation {


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Member member = new Member();

    @Test
    @DisplayName("정상검증")
    void userNameTest() {
        member.setUserName("이영진");
        member.setUserId("dudwls0505");
        member.setUserPw("juliet1225");
        member.setUserEmail("dudwls0505@naver.com");
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        for (ConstraintViolation<Member> violation : violations) {
            System.out.println("violation= " + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Size검증")
    void userNameTest2() {
        member.setUserName("가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가");
        member.setUserId("asdkljasdklasjdlkajdlkasjdaskldjasldjdsadjsakdhkdjhasjkdhaskdjhaskldhaskldhkasjdhaskjldaskljdhjk");
        member.setUserPw("가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가가");
        member.setUserEmail("dudwls0505@naver.com");
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        for (ConstraintViolation<Member> violation : violations) {
            System.out.println("violation= " + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

        assertThat(violations.size()).isEqualTo(4);
    }


    @Test
    @DisplayName("NotBlank검증")
    void userNameTest3() {
        member.setUserName("");
        member.setUserId("");
        member.setUserPw("");
        member.setUserEmail("");
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        for (ConstraintViolation<Member> violation : violations) {
            System.out.println("violation= " + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

        assertThat(violations.size()).isEqualTo(4);
    }


    @Test
    @DisplayName("정규패턴 검증")
    void userNameTest4() {
        member.setUserName("이영진sadsad");
        member.setUserId("dudwls0505!");
        member.setUserPw("한글들어오나요");
        member.setUserEmail("dudwls0505@naver.com");
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        for (ConstraintViolation<Member> violation : violations) {
            System.out.println("violation= " + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

        assertThat(violations.size()).isEqualTo(0);
    }




}

package git.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PasswordEncryptTest {


    @Test
    @DisplayName("암호화 테스트")
    void passwordEncode() {
        String raw = "1234";
        String encodedPassword = PasswordEncrypt.encrypt(raw);
        assertThat(PasswordEncrypt.isMatch(raw, encodedPassword)).isTrue();

        String raw2 = "1234";
        assertThat(PasswordEncrypt.isMatch(raw2, encodedPassword)).isTrue();
    }

}
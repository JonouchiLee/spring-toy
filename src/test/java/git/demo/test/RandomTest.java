package git.demo.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

class RandomTest {

    @Test
    @DisplayName("Random함수 테스트")
    void randomTest() {
        int i = 0;
        while (i < 3) {
            Random random = new Random(10);
            for (int j = 0; j < 5; j++) System.out.printf("%.5f  ", random.nextDouble());
            i++;
            System.out.println();
        }
    }

    @Test
    @DisplayName("SecureRandom함수 테스트")
    void secureRandomTest() throws NoSuchAlgorithmException {
        int i = 0;
        while (i < 3) {
            SecureRandom random = SecureRandom.getInstanceStrong();
            //  getInstanceStrong() : 보안속성에 지정된 알고리즘을 사용하여 SecureRandom 객체를 반환한다.
            for (int j = 0; j < 5; j++) System.out.printf("%.5f  ", random.nextDouble());
            i++;
            System.out.println();
        }
    }
}

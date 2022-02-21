package git.demo.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Fail.fail;


 class JDBCTests {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("JDBC 연결테스트")
    void testConnection() {
        try(Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/tobi?serverTimezone=Asia/Seoul", "root", "root")){


        }catch (Exception e){
            fail(e.getMessage());
        }
    }
}

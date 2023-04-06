package grade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 성적에 대한 동작을 정의하는 class
 */
public class GradeDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public GradeDAO() {
        try {
            String dbURL = "";
            String dbID = "root";
            String dbPassword = "";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

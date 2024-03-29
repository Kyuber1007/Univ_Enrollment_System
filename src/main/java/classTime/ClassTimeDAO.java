package classTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 수업 시각 객체를 활용하기 위한 객체
 */
public class ClassTimeDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public ClassTimeDAO() {
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

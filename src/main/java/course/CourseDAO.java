package course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * course 객체에 대한 연산을 나타내기 위한 class
 */
public class CourseDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public CourseDAO() {
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

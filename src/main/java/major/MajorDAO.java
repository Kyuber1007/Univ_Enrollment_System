package major;

import lecturer.Lecturer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 전공에 관한 동작 정의 class
 */
public class MajorDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public MajorDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3307/DB2017029952?serverTimezone=Asia/Seoul";
            String dbID = "root";
            String dbPassword = "skyyeo83!";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * majorID 를 통해 전공명을 return
     *
     * @param majorID
     * @return
     */
    public String findMajorNameByMajorID(String majorID) {
        String SQL = "select * from major where major_id = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, majorID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Major> showAllMajor() {
        ArrayList<Major> list = new ArrayList<>();
        String SQL = "select * from major;";
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Major major = new Major();
                major.setMajorID(rs.getString(1));
                major.setName(rs.getString(2));
                list.add(major);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

package lecturer;

import class_package.ClassObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 교수 동작을 정의하는 class
 */
public class LecturerDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public LecturerDAO() {
        try {
            String dbURL = "";
            String dbID = "root";
            String dbPassword = "";
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    /**
     * 교수 코드로 교수의 이름을 return함
     *
     * @param lecturerID
     * @return
     */
    public String findLecturerNameByLecturerID(String lecturerID) {
        String SQL = "select * from lecturer where lecturer_id = ?";

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, lecturerID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * lecturer 전체를 보여줌
     * 
     * @return
     */
    public ArrayList<Lecturer> showAllLecturer() {
        ArrayList<Lecturer> list = new ArrayList<>();
        String SQL = "select * from lecturer;";
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerID(rs.getString(1));
                lecturer.setName(rs.getString(2));
                lecturer.setMajorID(rs.getString(3));
                list.add(lecturer);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
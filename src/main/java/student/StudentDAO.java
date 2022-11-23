package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 학생 동작 정의 class
 */
public class StudentDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StudentDAO() {
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
     * login 버튼을 클릭했을 때, 실행되는 함수
     * 해당 student_id 와 password를 가지는 학생이 있는지 검사하고,
     * 유형에 따라 다른 값들을 return 함.
     * @param studentID
     * @param password
     * @return
     */
    public int login(String studentID, String password) {
        String SQL = "SELECT password, student_id FROM student WHERE student_id= ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    return 1; //로그인 성공
                } else {
                    return 0; // 비밀번호가 틀림
                }
            }
            return -1; // 아이디가 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2; // 데이터베이스 오류
    }

    /**
     * 학생이 본인의 정보를 수정하기 위해 정보를 요청할 때 호출되는 함수.
     * 즉, 학생 메인 페이지에서 정보 버튼을 클릭하면 호출됨.
     * @param studentID
     * @return
     */
    public Student getInfo(String studentID) {
        String SQL = "select * from student where student_id=?";
        Student student = new Student();
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                student.setStudentID(rs.getString(1));
                student.setPassword(rs.getString(2));
                student.setName(rs.getString(3));
                student.setSex(rs.getString(4));
                student.setMajorID(rs.getString(5));
                student.setLecturerID(rs.getString(6));
                student.setYear(rs.getString(7));
                student.setStatus(rs.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    /**
     * 학생이 본인의 정보 page 에서 비밀번호를 바꿀시 실행되는 함수
     * @param studentID
     * @param password
     * @return
     */
    public int changePassword(String studentID, String password) {
        String SQL = "update student\n" +
                "set password=?" +
                "where student_id = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, password);
            pstmt.setString(2, studentID);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

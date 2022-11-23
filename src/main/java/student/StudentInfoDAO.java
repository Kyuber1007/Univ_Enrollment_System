package student;

import grade.Grade;
import grade.GradeDAO;

import java.sql.*;

public class StudentInfoDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StudentInfoDAO() {
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
     * 학생의 전체 평균 학점을 계산하는 함수
     * @param studentID
     * @return 평균 학점
     */
    public String getAverageGrade(String studentID) {
        String SQL = "select CR.student_id, CR.grade, C.credit\n" +
                "from credits as CR, course as C\n" +
                "where CR.course_id = C.course_id and CR.student_id = ?;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();

            int credits = 0;
            double tem = 0;

            while (rs.next()) {
                String grade = rs.getString(2);
                int credit = Integer.parseInt(rs.getString(3));
                credits += credit;
                switch (grade) {
                    case "A+":
                        tem += 4.5 * credit;
                        break;
                    case "A0":
                        tem += 4.0 * credit;
                        break;
                    case "B+":
                        tem += 3.5 * credit;
                        break;
                    case "B0":
                        tem += 3.0 * credit;
                        break;
                    case "C+":
                        tem += 2.5 * credit;
                        break;
                    case "C0":
                        tem += 2.0 * credit;
                        break;
                    case "D+":
                        tem += 1.5 * credit;
                        break;
                    case "D0":
                        tem += 1.0 * credit;
                        break;
                    default:
                        tem += 0.0 * credit;
                        break;
                }
            }
            return String.format("%.3f", tem / credits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

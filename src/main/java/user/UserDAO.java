package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public UserDAO() {
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

    public String test(String userID, String userPassword) {
        String test = userID + " " + userPassword;
        return test;
    }

    public int login(String userID, String userPassword, String userType) {
        String SQL = "SELECT userPassword, studentID FROM student WHERE studentID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(userPassword)) {
                    if (rs.getString(2) != null)
                        if (rs.getString(2).equals(userType))
                            return 1; // 로그인 성공
                        else
                            return 0; // usertype 존재하는데 불일치
                    else
                        return 1;
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

    public int join(User user) {
        String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            pstmt.setString(6, user.getUserType());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 데이터베이스 오류
    }

    public ArrayList<User> showAll() {
        String SQL = "SELECT * FROM USER";
        ArrayList<User> studentList = new ArrayList<User>();
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString(1));
                user.setUserPassword(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setUserGender(rs.getString(4));
                user.setUserEmail(rs.getString(5));
                user.setUserType(rs.getString(6));
                studentList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
}

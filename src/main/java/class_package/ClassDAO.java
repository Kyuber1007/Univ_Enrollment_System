package class_package;

import javax.swing.text.Style;
import java.sql.*;

public class ClassDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public ClassDAO() {
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

    /**
     * 각 수업에 대하여 정원을 조절하는 방식.
     * 새로 지정하고자 하는 정원이
     * 1. 현재 수강신청한 인원보다 많거나,
     * 2. 현재 배정된 강의실 인원보다 많으면
     * 새로운 인원 배정을 취소하고,
     * 위의 조건에 걸리지 않으면 수업의 정원을 바꿈.
     * 
     * @param classNO      정원을 바꾸고자 하는 수업 번호
     * @param newPersonMax 새로 지정하고자 하는 정원
     * @return
     */
    public int editPersonMax(String classNO, String newPersonMax) {
        String SQL = "select CL.class_no, R.room_id, R.occupancy \n" +
                "from room as R, class as CL\n" +
                "where R.room_id = CL.room_id and CL.opened = \"2023\"" +
                "and CL.class_no = ?;\n";
        String SQL3 = "select count(*)\n" +
                "from class as CL, enrollment as E\n" +
                "where CL.class_no = E.class_no\n" +
                "and CL.opened = '2023'\n" +
                "and CL.class_no = ?;";
        try {
            int currentEnrolled = 0;
            pstmt = conn.prepareStatement(SQL3);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                currentEnrolled = Integer.parseInt(rs.getString(1));
            } else {
                currentEnrolled = 0;
            }

            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String occupancy = rs.getString(3);
                int newPersonMaxInt = Integer.parseInt(newPersonMax);
                if (currentEnrolled > newPersonMaxInt || newPersonMaxInt > Integer.parseInt(occupancy)
                        || newPersonMaxInt <= 0) {
                    return 0;
                } else {
                    String SQL2 = "update class\n" +
                            "set person_max=?\n" +
                            "where opened='2023' and class_no = ?";
                    pstmt = conn.prepareStatement(SQL2);
                    pstmt.setString(1, newPersonMax);
                    pstmt.setString(2, classNO);
                    pstmt.executeUpdate();
                    return 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

}

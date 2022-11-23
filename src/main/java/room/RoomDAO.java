package room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 강의실 동작에 대한 class
 */
public class RoomDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public RoomDAO()
    {
        try{
            String dbURL = "jdbc:mysql://localhost:3307/DB2017029952?serverTimezone=Asia/Seoul";
            String dbID = "root";
            String dbPassword = "skyyeo83!";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

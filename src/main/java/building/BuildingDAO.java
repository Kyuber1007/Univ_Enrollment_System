package building;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BuildingDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public BuildingDAO() {
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

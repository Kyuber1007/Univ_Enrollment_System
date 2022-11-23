package searching;

import time_operator.TimeOperator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 검색 동작
 */
public class SearchingDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public SearchingDAO() {
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
     * 검색을 할 때, 공통적으로 수행되는 부분을 묶어놓은 것
     * course_id, course_name, class_no를 가지고 검색한 result set 들을
     * 수강편람 형식에 맞게 모든 정보를 취합하여 출력하도록 함.
     * @param rs
     * @return
     */
    public ArrayList<SearchingResult> searchCommonPart(ResultSet rs) {
        try {
            TimeOperator changeTimeFormat = new TimeOperator();
            ArrayList<SearchingResult> list = new ArrayList<SearchingResult>();
            while (rs.next()) {
                SearchingResult searchingResult = new SearchingResult();
                searchingResult.setClassNO(rs.getString(1));
                searchingResult.setCourseID(rs.getString(2));
                searchingResult.setCourseName(rs.getString(3));
                searchingResult.setCredit(rs.getString(4));
                searchingResult.setLecturerName(rs.getString(5));
                String SQL2 = "select CL.class_no, count(*)\n" +
                        "from class as CL, enrollment as E\n" +
                        "where CL.class_no = ? \n" +
                        "and E.class_no = CL.class_no \n" +
                        "and CL.opened = '2023';";
                pstmt = conn.prepareStatement(SQL2);
                pstmt.setString(1, searchingResult.getClassNO());
                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.next()) {
                    searchingResult.setEnrollmentCount(rs2.getString(2));
                } else {
                    searchingResult.setEnrollmentCount("0");
                }

                searchingResult.setPersonMax(rs.getString(6));
//              시간을 한개의 컬럼에 오도록 설정함.
                searchingResult.setTime(changeTimeFormat.showBeingEndToNormalForm(rs.getString(7), rs.getString(8)));
                searchingResult.setBuildingName(rs.getString(9) + ", " + rs.getString(10));
                list.add(searchingResult);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * searching with CourseName
     * 이름의 일부만 입력해도 결과를 출력하도록 구현
     * @param courseName
     * @return
     */
    public ArrayList searchByCourseName(String courseName) {
        String SQL = "select CL.class_no, CO.course_id, \n" +
                "CO.name, CL.credit, L.name, CL.person_max, group_concat(T.begin) as begin, group_concat(T.end) as end, \n" +
                "B.name as building, R.room_id\n" +
                "from course as CO, class as CL, lecturer as L, time as T, room as R, Building as B\n" +
                "where CO.course_id = CL.course_id \n" +
                "and CL.opened ='2023' \n" +
                "and CL.lecturer_id = L.lecturer_id \n" +
                "and CO.name like " + "\"%\"" + "?" + "\"%\"" + "\n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, R.room_id, B.name ;";

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, courseName);
            rs = pstmt.executeQuery();

            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * course_id를 정확하게 입력하면 검색 결과를 내도록 구현
     * @param courseID
     * @return
     */
    public ArrayList searchByCourseID(String courseID) {
        String SQL = "select CL.class_no, CO.course_id, \n" +
                "CO.name, CL.credit, L.name, CL.person_max, group_concat(T.begin) as begin, group_concat(T.end) as end, \n" +
                "B.name as building, R.room_id\n" +
                "from course as CO, class as CL, lecturer as L, time as T, room as R, Building as B\n" +
                "where CO.course_id = ?\n" +
                "and CO.course_id = CL.course_id \n" +
                "and CL.opened =\"2023\" \n" +
                "and CL.lecturer_id = L.lecturer_id \n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, R.room_id, B.name ;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, courseID);
            rs = pstmt.executeQuery();

            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 수업번호를 정확하게 입력하면 검색결과를 출력하도록 구현
     * @param classNO
     * @return
     */
    public ArrayList searchByClassNO(String classNO) {
        String SQL = "select CL.class_no, CO.course_id, \n" +
                "CO.name, CL.credit, L.name, CL.person_max, group_concat(T.begin) as begin, group_concat(T.end) as end, \n" +
                "B.name as building, R.room_id\n" +
                "from course as CO, class as CL, lecturer as L, time as T, room as R, Building as B\n" +
                "where CL.class_no = ?\n" +
                "and CO.course_id = CL.course_id \n" +
                "and CL.opened =\"2023\" \n" +
                "and CL.lecturer_id = L.lecturer_id \n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, R.room_id, B.name ;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();

            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 검색화면에서 어떠한 것도 검색하지 않았을 때, 모든 개설된 수업들을 보여주기위한 함수
     * @return
     */
    public ArrayList<SearchingResult> showAllClass() {
        String SQL = "select CL.class_no, CO.course_id, \n" +
                "CO.name, CL.credit, L.name, CL.person_max, group_concat(T.begin) as begin, group_concat(T.end) as end, \n" +
                "B.name as building, R.room_id\n" +
                "from course as CO, class as CL, lecturer as L, time as T, room as R, Building as B\n" +
                "where CO.course_id = CL.course_id \n" +
                "and CL.opened ='2023' \n" +
                "and CL.lecturer_id = L.lecturer_id \n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, R.room_id, B.name;";
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SearchingResult> showStudentClass(String studentID) {
        String SQL = "select CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, \n" +
                "group_concat(T.begin) as begin, group_concat(T.end) as end\n" +
                ",B.name as building, R.room_id\n" +
                "from course as CO, class as CL, enrollment as E,lecturer as L, time as T, room as R, Building as B, Student as S\n" +
                "where E.student_id = S.student_id \n" +
                "and CO.course_id = CL.course_id\n" +
                "and S.student_id = ?\n" +
                "and E.class_no = CL.class_no\n" +
                "and CL.opened =\"2023\"\n" +
                "and CL.lecturer_id = L.lecturer_id\n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CL.credit, CO.name, L.name, CL.person_max, R.room_id, B.name;";
        try {
            System.out.println("studen_id nina dsf" + studentID);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SearchingResult> showStudentWantedClass(String studentID) {
        String SQL = "select CL.class_no, CO.course_id, CO.name, CL.credit, L.name, CL.person_max, \n" +
                "group_concat(T.begin) as begin, group_concat(T.end) as end\n" +
                ",B.name as building, R.room_id\n" +
                "from course as CO, class as CL, wanted_enrollment as E,lecturer as L, time as T, room as R, Building as B, Student as S\n" +
                "where E.student_id = S.student_id \n" +
                "and CO.course_id = CL.course_id\n" +
                "and S.student_id = ?\n" +
                "and E.class_no = CL.class_no\n" +
                "and CL.opened =\"2023\"\n" +
                "and CL.lecturer_id = L.lecturer_id\n" +
                "and T.class_id = CL.class_id\n" +
                "and R.room_id = CL.room_id\n" +
                "and B.building_id = R.building_id\n" +
                "group by CL.class_no, CO.course_id, CL.credit, CO.name, L.name, CL.person_max, R.room_id, B.name;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            return searchCommonPart(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

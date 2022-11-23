package admin;

import java.sql.*;
import java.util.ArrayList;

import course_grade_list.CourseGrade;
import student.Student;
import student.StudentInfo;
import student.StudentInfoDAO;
import time_operator.TimeOperator;


/**
 * 관리자가 할 수 있는 동작들 정의하는 class
 */
public class AdminDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private double totalAverage;

    public AdminDAO() {
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
     * 관리자 로그인 action
     * 초기 화면에서 관리자 로그인을 선택하고, 비어있는 칸을 입력한 후
     * 로그인 버튼을 눌렀을 때 실행됨.
     *
     * @param adminID
     * @param adminPassword
     * @param adminCode
     * @return
     */
    public int login(String adminID, String adminPassword, String adminCode) {
        String SQL = "SELECT password, admin_code FROM admin WHERE admin_id = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, adminID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(adminPassword) && rs.getString(2).equals(adminCode)) {
                    return 1; //로그인 성공
                } else {
                    return 0; //비밀번호 불일치 or code 불일치
                }
            }
            return -1; // 아이디가 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2; // 데이터베이스 오류
    }

    /**
     * 관리자 회원가입 action
     *
     * @param admin
     * @return
     */
    public int join(Admin admin) {
        String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, admin.getAdminID());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getAdminCode());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 데이터베이스 오류
    }

    /**
     * 모든 학생 정보보기 버튼을 눌렀을 때 호출되는 함수
     * 학생의 인적사항을 조회할 수 있도록, 필요한 것들을
     * DB에서 추출해서 list를 return 함
     *
     * @return
     */
    public ArrayList<StudentInfo> showAllStudent() {
        String SQL = "select S.student_id, S.name, S.sex, M.name, L.name, S.year, S.status\n" +
                "from credits as CR, student as S, lecturer as L, major as M\n" +
                "where S.student_id = CR.student_id and L.lecturer_id = S.lecturer_id and M.major_id = S.major_id\n" +
                "group by (S.student_id);";
        ArrayList<StudentInfo> studentList = new ArrayList<StudentInfo>();
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentID(rs.getString(1));
                studentInfo.setName(rs.getString(2));
                studentInfo.setSex(rs.getString(3));
                studentInfo.setMajorName(rs.getString(4));
                studentInfo.setLecturerName(rs.getString(5));
                studentInfo.setYear(rs.getString(6));
                studentInfo.setStatus(rs.getString(7));
                studentInfo.setGrade(studentInfoDAO.getAverageGrade(studentInfo.getStudentID()));
                studentList.add(studentInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * 모든 학생들을 조회하고, 특정 학생을 변경하고자 할 때, 호출되는 함수
     * 특정 학번을 입력버튼을 눌러서, 인적사항 변경 page에서
     * 변경 버튼을 눌렀을 때 호출함
     *
     * @param student
     * @return
     */
    public int changeStudent(Student student) {
        String SQL = "update student set password=?, name=?, sex=?, major_id=?, lecturer_id=?, year=?, status=? where student_id=?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(8, student.getStudentID());
            pstmt.setString(1, student.getPassword());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getSex());
            pstmt.setString(4, student.getMajorID());
            pstmt.setString(5, student.getLecturerID());
            pstmt.setString(6, student.getYear());
            pstmt.setString(7, student.getStatus());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 입력창으로 수업번호를 입력받고, 폐강 버튼을 눌렀을 때 호출되는 함수
     * 해당 class의 정보들을 불러오고,
     * 각각 class, time, enrollment, wantedenrollment에서 삭제함.
     *
     * @param classNO
     * @return
     */
    public int deleteClass(String classNO) {
        // class, time, enrollment, want_to_enrollment table에서 삭제해야함
        String classID = null;
        String getClassIDSQL = "select CL.class_id\n" +
                "from time as T, class as CL\n" +
                "where T.class_id = CL.class_id \n" +
                "and CL.opened = '2023' \n" +
                "and CL.class_no = ?\n" +
                "group by CL.class_id;";
        String deleteClassTableSQL = "delete from class\n" +
                "where class_no=? and opened = '2023';";

        String deleteTimeTableSQL = "delete from time\n" +
                "where class_id=?";
        String deleteEnrollmentTableSQL = "delete from enrollment\n" +
                "where class_no = ?;";
        String deleteWantToEnrollmentTableSQL = "delete from wanted_enrollment\n" +
                "where class_no = ?";

        try {
            pstmt = conn.prepareStatement(deleteEnrollmentTableSQL);
            pstmt.setString(1, classNO);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(deleteWantToEnrollmentTableSQL);
            pstmt.setString(1, classNO);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(getClassIDSQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                classID = rs.getString(1);
            }

            if (classID != null) {
                pstmt = conn.prepareStatement(deleteTimeTableSQL);
                pstmt.setString(1, classID);
                pstmt.executeUpdate();
            }
            pstmt = conn.prepareStatement(deleteClassTableSQL);
            pstmt.setString(1, classNO);
            pstmt.executeUpdate();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 수업 개설하는 함수.
     * 5가지 조건을 만족하면 새로운 수업을 만들 수 있음.
     * 1. 해당 교강사가 요청하는 시간에 강의를 진행 중인지 -> 구현 진행중
     * 2. 해당 강의실이 요청하는 시간에 강의할 때 사용되는지 -> 구현 진행중
     * 3. 일요일 과목 개설 불가
     * 4. 강의실 인원
     * 5. class_no 검사 및 course_id 유효 검사
     * <p>
     * 수업 생성에 성공하면 class table에 추가하고, time table 에도 해당 시간대를 추가 해야함.
     * time table에 class_id foreign key constraint가 있어서
     * 먼저 class table에 추가하고 time table에 추가해야함.
     * 따라서, class table에 일단 추가하고, time table에 추가를 실패했을 때,
     * 다시 class table에서 해당 수업을 삭제하는 형태로 코드 진행.
     *
     * @param courseID
     * @param classNO
     * @param classID
     * @param personMax
     * @param year
     * @param lecturerID
     * @param majorID
     * @param timeID
     * @param begin
     * @param end
     * @param roomID
     * @return
     */
    public int createClass(String courseID, String classNO, String classID, String personMax, String year, String lecturerID, String majorID, String timeID, String begin, String end, String roomID) {
        int result = -9;

        TimeOperator timeOperator = new TimeOperator();
        String[] beginArray = begin.split(",");
        String[] endArray = end.split(",");
        String[] periodArray = timeOperator.changeToPeriod(beginArray, endArray);
        for (int i = 0; i < periodArray.length; i++) {
            System.out.println(periodArray[i]);
            System.out.println(beginArray[i]);
            System.out.println(endArray[i]);
        }
        String courseName = null;
        String credit = null;
        String occupancy;

        try {
            String SQL2 = "select *\n" +
                    "from room as R\n" +
                    "where R.room_id = ?";

            pstmt = conn.prepareStatement(SQL2);
            pstmt.setString(1, roomID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                occupancy = rs.getString(3);
                if (Integer.parseInt(personMax) > Integer.parseInt(occupancy)) {
                    // 정원 초과
                    return -2;
                }
            } else {
                // 존재하지 않는 room_id 입력
                return -1;
            }

            // 일요일 생성시 오류
            if (!beginArray[0].equals("NO")) {
                periodArray = timeOperator.changeToPeriod(beginArray, endArray);
                for (int i = 0; i < periodArray.length; i++) {
                    if (periodArray[i].substring(0, 2).equals("07"))
                        return -3;
                }
            }

            // course_id 없을 경우 오류
            String SQL = "select C.name, C.credit\n" +
                    "from course as C\n" +
                    "where C.course_id = ?";

            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, courseID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                courseName = rs.getString(1);
                credit = rs.getString(2);
            } else {
                return -4;
            }

            // 해당 시간대에 요청하는 강의실을 사용하는지 검사하기
            String SQL7 = "select CL.class_id, T.begin, T.end, CL.room_id\n" +
                    "from time as T, class as CL\n" +
                    "where T.class_id = CL.class_id and CL.opened = '2023' and CL.room_id = ?;";
            pstmt = conn.prepareStatement(SQL7);
            pstmt.setString(1, roomID);
            rs = pstmt.executeQuery();
            boolean timeCheckWithRoom = false;
            while (rs.next()) {
                String periodOriginal = timeOperator.changeToPeriod(rs.getString(2), rs.getString(3));
                for (int i = 0; i < periodArray.length; i++) {
                    System.out.println("강의실 신청하고자 하는 시간대: " + periodArray[i]);
                    System.out.println("강의실 원래 시간대: " + periodOriginal);
                    timeCheckWithRoom = timeOperator.compareTime(periodArray[i], periodOriginal);
                    if (!timeCheckWithRoom) {
                        return -5;
                    }
                }
            }

            // 해당 시간대에 강사가 강의 중인지 검사하기
            String SQL8 = "select CL.class_id, T.begin, T.end\n" +
                    "from time as T, class as CL, lecturer as L\n" +
                    "where T.class_id = CL.class_id \n" +
                    "and CL.opened = '2023' \n" +
                    "and L.lecturer_id = ? \n" +
                    "and L.lecturer_id = CL.lecturer_id;";
            pstmt = conn.prepareStatement(SQL8);
            pstmt.setString(1, lecturerID);
            pstmt.executeQuery();
            rs = pstmt.executeQuery();

            boolean timeCheckWithLecturer = false;
            while (rs.next()) {
                String periodOriginal = timeOperator.changeToPeriod(rs.getString(2), rs.getString(3));

                for (int i = 0; i < periodArray.length; i++) {
                    if (periodArray[i].substring(0, 2).equals(periodOriginal.substring(0, 2))) {
                        System.out.println("교수 신청하고자 하는 시간대: " + periodArray[i]);
                        System.out.println("교수 강의 중인 원래 시간대: " + periodOriginal);
                        timeCheckWithLecturer = timeOperator.compareTime(periodArray[i], periodOriginal);
                        if (!timeCheckWithLecturer) {
                            return -6;
                        }
                    }
                }
            }

            String SQL4 = "insert into class\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, '2023', ?);";
            pstmt = conn.prepareStatement(SQL4);
            pstmt.setString(1, classID);
            pstmt.setString(2, classNO);
            pstmt.setString(3, courseID);
            pstmt.setString(4, courseName);
            pstmt.setString(5, majorID);
            pstmt.setString(6, year);
            pstmt.setString(7, credit);
            pstmt.setString(8, lecturerID);
            pstmt.setString(9, personMax);
            pstmt.setString(10, roomID);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }

        if (!beginArray[0].equals("NO")) {
            try {
                if (result > 0) {
                    for (int i = 0; i < periodArray.length; i++) {
                        String SQL6 = "insert into time values\n" +
                                "(?, ?, ?, ? ,?)";
                        pstmt = conn.prepareStatement(SQL6);
                        pstmt.setString(1, timeID + i);
                        pstmt.setString(2, classID);
                        pstmt.setString(3, String.valueOf(i));
                        pstmt.setString(4, beginArray[i]);
                        pstmt.setString(5, endArray[i]);
                        pstmt.executeUpdate();
                    }
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = -9;
            }
        }

        if (result == -9) {
            String SQL6 = "delete from class\n" +
                    "where class_id = ? and class_no = ? \n" +
                    "and  course_id = ? and name = ?\n" +
                    "and major_id = ? and year = ?\n" +
                    "and credit =  ? and lecturer_id = ?\n" +
                    "and person_max = ? and opened = '2023' and room_id = ?);";
            try {
                pstmt = conn.prepareStatement(SQL6);
                pstmt.setString(1, classID);
                pstmt.setString(2, classNO);
                pstmt.setString(3, courseID);
                pstmt.setString(4, courseName);
                pstmt.setString(5, majorID);
                pstmt.setString(6, year);
                pstmt.setString(7, credit);
                pstmt.setString(8, lecturerID);
                pstmt.setString(9, personMax);
                pstmt.setString(10, roomID);
                return -9;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    /**
     * 관리자 메인 페이지에서 OLAP 기능을 수행하도록 선택했을 때 호출되는 함수.
     * OLAP 를 위한 연산을 진행하는 함수.
     * 전체 평균 평점을 구하고, 특정 과목의 평균 평점을 구해서,
     * course_grade table에 각각 결과를 넣어줌.
     *
     * @return
     */
    public int calculateOLAP() {
        String SQL = "select CR.course_id, group_concat(CR.grade), C.credit\n" +
                "from credits CR, course as C\n" +
                "where CR.course_id = C.course_id\n" +
                "group by CR.course_id";
        ArrayList<CourseGrade> courseGradeList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CourseGrade courseGrade = new CourseGrade();
                courseGrade.setCourseID(rs.getString(1));
                courseGrade.setGrades(rs.getString(2));
                courseGrade.setGradeNum(rs.getString(2).split(",").length);
                courseGrade.setCredit(Integer.parseInt(rs.getString(3)));
                courseGrade.setAverageGrade(calculateAverageGrade(courseGrade));
                courseGradeList.add(courseGrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        int totalCredit = 0;
        double totalGrade = 0;

        for (int i = 0; i < courseGradeList.size(); i++) {
            CourseGrade tem = courseGradeList.get(i);
            totalGrade += tem.getAverageGrade() * tem.getCredit() * tem.getGradeNum();
            totalCredit += tem.getCredit() * tem.getGradeNum();
        }
        double totalAverageGrade = totalGrade / totalCredit;
        totalAverage = totalAverageGrade;
        System.out.println("total average: " + totalAverageGrade);

        String SQL2 = "truncate course_grade";
        try {
            pstmt = conn.prepareStatement(SQL2);
            pstmt.executeUpdate();

            for (int i = 0; i < courseGradeList.size(); i++) {
                CourseGrade tem = courseGradeList.get(i);
                tem.setDifferenceAverageAndCourse(totalAverageGrade - tem.getAverageGrade());

                String SQL3 = "insert course_grade value(?,?,?)";
                pstmt = conn.prepareStatement(SQL3);
                pstmt.setString(1, tem.getCourseID());
                pstmt.setDouble(2, tem.getAverageGrade());
                pstmt.setDouble(3, tem.getDifferenceAverageAndCourse());
                pstmt.executeUpdate();
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 특정 과목의 평균 평점을 계산함.
     *
     * @param courseGrade
     * @return
     */
    private double calculateAverageGrade(CourseGrade courseGrade) {
        String[] grades = courseGrade.getGrades().split(",");
        double tem = 0;

        for (int i = 0; i < grades.length; i++) {
            switch (grades[i]) {
                case "A+":
                    tem += 4.5;
                    break;
                case "A0":
                    tem += 4.0;
                    break;
                case "B+":
                    tem += 3.5;
                    break;
                case "B0":
                    tem += 3.0;
                    break;
                case "C+":
                    tem += 2.5;
                    break;
                case "C0":
                    tem += 2.0;
                    break;
                case "D+":
                    tem += 1.5;
                    break;
                case "D0":
                    tem += 1.0;
                    break;
                default:
                    tem += 0.0;
                    break;
            }
        }
        return tem / courseGrade.getGradeNum();
    }

    /**
     * OLAP 결과를 보여줌
     * 최상위 10개의 row만 보여줌
     * 만약 (전체 평균 - 특정 과목 평균)이 같은 값을 가지는 것이 있을 땐,
     * 학수번호의 사전순으로 짜름.
     *
     * @return
     */
    public ArrayList showOLAP() {
        String SQL = "select * from course_grade order by diff desc," +
                " course_id asc limit 10";
        ArrayList<CourseGrade> list = new ArrayList();
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CourseGrade courseGrade = new CourseGrade();
                courseGrade.setCourseID(rs.getString(1));
                courseGrade.setAverageGrade(rs.getDouble(2));
                courseGrade.setDifferenceAverageAndCourse(rs.getDouble(3));
                courseGrade.setTotalAverage(totalAverage);
                list.add(courseGrade);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

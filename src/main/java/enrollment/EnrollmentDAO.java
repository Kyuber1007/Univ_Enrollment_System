package enrollment;

import searching.SearchingResult;
import time_operator.TimeOperator;

import java.sql.*;
import java.util.ArrayList;

/**
 * 수강신청을 진행하기 위한 객체
 */
public class EnrollmentDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public EnrollmentDAO() {
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
     * 수강신청을 희망하는 학생에 대해서 현재 해당 학생이 신청한 총 학점을 계산하여
     * 새로운 해당 강의를 신청했을 때, credit 제한에 걸리지 않는지 검사함.
     *
     * @param studentID 수강신청 하고자 하는 학생의 ID.
     * @param credit    수강신청을 희망하는 과목의 credit.
     * @return 수강신청이 가능한 상태면 1을 return하고 그렇지 못하면 -1, database 오류라면 0을 return 함.
     */
    public int checkCredit(String studentID, String credit) {
        String SQL = "select student_id, sum(credit) as credit_sum\n" +
                "from enrollment\n" +
                "where student_id = ?\n" +
                "group by (student_id);";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (Double.parseDouble(rs.getString(2)) + Double.parseDouble(credit) > 18) {
                    return -1;
                } else return 1;
            } else return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public boolean checkReEnrollmentBoolean(String studentID, String courseID) {
        String SQL = "select S.student_id, C.course_id, CR.grade\n" +
                "from course as C, credits as CR, student as S\n" +
                "where S.student_id = CR.student_id and C.course_id = CR.course_id\n" +
                "and S.student_id = ?\n" +
                "and C.course_id = ?;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, courseID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 재수강 자격에 대한 검사.
     * 이전에 수강한 기록의 grade 를 불러와서 재수강 가능 여부를 확인함.
     *
     * @param studentID 수강신청 하고자 하는 학생의 ID.
     * @param courseID  수강신청 하고자 하는 학수번호
     * @return 수강신청이 가능하면 1, 불가능하면 -2, database 오류라면 0을 return함.
     */
    public int checkReEnrollment(String studentID, String courseID) {
        String SQL = "select S.student_id, C.course_id, CR.grade\n" +
                "from course as C, credits as CR, student as S\n" +
                "where S.student_id = CR.student_id and C.course_id = CR.course_id\n" +
                "and S.student_id = ?\n" +
                "and C.course_id = ?;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, courseID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                String grade = rs.getString(3);
                if (grade.equals("A+") || grade.equals("A0") || grade.equals("B+") || grade.equals("B0"))
                    return -2;
                else return 10;
            } else return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 수강신청 하고자 하는 수업의 정원을 확인하여 수강신청 가능여부를 판단.
     *
     * @param classNO 수강신청 하고자 하는 수업번호
     * @return 수강신청이 가능하면 1, 불가능하면 -3, database 오류라면 0을 return 함.
     */
    public int checkPersonMax(String classNO) {
//        enrollment가 없을 때, SQL은 아무런 결과가 나오지 않음. 따라서, 구분해서 정의해야함.
        String SQL = "select CL.class_no, count(*)\n" +
                "from class as CL, enrollment as E\n" +
                "where CL.opened='2023' " +
                "and E.class_no = CL.class_no " +
                "and CL.class_no = ?\n" +
                "group by CL.class_no;";

        String SQL2 = "select CL.person_max\n" +
                "from class as CL\n" +
                "where CL.class_no = ? and CL.opened = '2023';\n";
        String personMax = "0";
        String enrollment = "0";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                enrollment = rs.getString(2);
                System.out.println("enrollment:" + enrollment);
            } else {
                enrollment = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pstmt = conn.prepareStatement(SQL2);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                personMax = rs.getString(1);
                System.out.println("personMax: " + personMax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        if (Integer.parseInt(enrollment) >= Integer.parseInt(personMax)) {
            return -3;
        }
        return 1;
    }

    /**
     * 수강신청을 희망하는 학새의 수강신청 내역에 따른 시간을 파악하여
     * 해당 수업을 신청했을 때, 시간의 겹침 유무를 판단함
     *
     * @param studentID
     * @param classNO
     * @return 수강신청 가능하면 1, 불가능하면 -4, database 오류라면 0을 return 함.
     */
    public int checkTimeAvailability(String studentID, String classNO) {
        TimeOperator timeOperator = new TimeOperator();
//      현재 추가하고 싶은 수업
        String SQL = "select group_concat(T.begin) as begin, group_concat(T.end) as end\n" +
                "from time as T, class as CL\n" +
                "where CL.class_no = ? and T.class_id = CL.class_id and CL.opened = '2023';";

//        이전에 수강신청한 모든 수업
        String SQL2 = "select S.student_id, group_concat(T.begin) as begin, group_concat(T.end) as end\n" +
                "from enrollment as E, student as S, Time as T, class as CL\n" +
                "where T.class_id = CL.class_id\n" +
                "and CL.class_no = E.class_no\n" +
                "and S.student_id = ?\n" +
                "and E.student_id = S.student_id\n" +
                "and CL.opened = '2023';";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();

            ResultSet rs2;
            pstmt = conn.prepareStatement(SQL2);
            pstmt.setString(1, studentID);
            rs2 = pstmt.executeQuery();
            if (timeOperator.compareTimeWithRS(rs, rs2))
                return 1;
            else return -4;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 수강신청을 희망하는 과목을 해당 학생이 이미 수강신청 했는지에 대해 판단함
     * 즉, 이미 수강신청을 한 과목에 대해서 다시 수강신청을 하고자 하는 지에 대해 판단함.
     *
     * @param studentID
     * @param classNO
     * @return 수강신청 가능하면 1, 불가능하면 -5, database 오류라면 0을 return 함.
     */
    private int checkAlreadyEnrolled(String studentID, String classNO) {
        String SQL = "select * from enrollment as E\n" +
                "where E.student_id = ?\n" +
                "and E.class_no = ?;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return -5;
            } else return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 수강 희망에 대한 함수
     * 이미 수강희망 과목에 해당 과목을 담았는 지에 대한 여부를 검사함
     *
     * @param studentID
     * @param classNO
     * @return 수강희망에 담을 수 있으면 1, 없으면 -1, database 오류라면 0을 return 함.
     */
    private int checkAlreadyWantedEnrolled(String studentID, String classNO) {
        String SQL = "select * from wanted_enrollment as E\n" +
                "where E.student_id = ?\n" +
                "and E.class_no = ?;";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return -1;
            } else return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 수강신청 function
     * 두가지 정보로 나머지 2개를 마저 찾아내 4개의 인자를 받는 enrollClass()를 호출함
     *
     * @param studentID
     * @param classNO
     * @return
     */
    public int enrollClass(String studentID, String classNO) {
        String SQL = "select C.course_id, CL.credit\n" +
                "from class as CL, course as C\n" +
                "where CL.course_id = C.course_id \n" +
                "and CL.class_no = ? and CL.opened = '2023';";
        String courseID = null, credit = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, classNO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                courseID = rs.getString(1);
                credit = rs.getString(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollClass(classNO, studentID, courseID, credit);
    }


    /**
     * 4개의 인자를 받아 수강신청.
     * 5가지 조건에 따라 검사를 진행한 후, 검사 결과 모두 통과되면 수강신청을 진행하고,
     * 하나라도 통과하지 못하면, 해당 오류 code를 return
     * 오류 출력 순서는 임의로 정했으나, 좁은 범위에서부터 넓은 범위로 퍼져나가게 순서를 정함.
     *
     * @param classNO
     * @param studentID
     * @param courseID
     * @param credit
     * @return
     */
    public int enrollClass(String classNO, String studentID, String courseID, String credit) {
        int checkPersonMaxResult = checkPersonMax(classNO);
        int checkReEnrollmentResult = checkReEnrollment(studentID, courseID);
        int checkCreditResult = checkCredit(studentID, credit);
        int checkTimeAvailabilityResult = checkTimeAvailability(studentID, classNO);
        int checkAlreadyEnrolledResult = checkAlreadyEnrolled(studentID, classNO);

        if (checkPersonMaxResult == 1 &&
                checkReEnrollmentResult == 1 &&
                checkCreditResult == 1 &&
                checkTimeAvailabilityResult == 1 &&
                checkAlreadyEnrolledResult == 1) {
            String SQL = "insert into enrollment values (?,?,?);";
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, studentID);
                pstmt.setString(2, classNO);
                pstmt.setString(3, credit);
                return pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            if (checkAlreadyEnrolledResult != 1) {
                return checkAlreadyEnrolledResult;
            } else if (checkCreditResult != 1) {
                return checkCreditResult;
            } else if (checkPersonMaxResult != 1) {
                return checkPersonMaxResult;
            } else if (checkTimeAvailabilityResult != 1) {
                return checkTimeAvailabilityResult;
            } else if (checkReEnrollmentResult != 1) {
                return checkReEnrollmentResult;
            }
            return 0;
        }
    }

    /**
     * 희망수업 목록에 추가함.
     * 이미 있는 경우를 검사하고, 없으면 추가함.
     *
     * @param studentID
     * @param classNO
     * @return
     */
    public int addWantedClass(String studentID, String classNO) {
        String SQL = "select C.course_id, CL.credit\n" +
                "from class as CL, course as C\n" +
                "where CL.course_id = C.course_id \n" +
                "and CL.class_no = ? and CL.opened = '2023';";

        String courseID = null, credit = null;
        if (checkAlreadyWantedEnrolled(studentID, classNO) == 1) {
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, classNO);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    courseID = rs.getString(1);
                    credit = rs.getString(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }

            String SQL2 = "insert into wanted_enrollment values (?,?,?);";
            try {
                pstmt = conn.prepareStatement(SQL2);
                pstmt.setString(1, studentID);
                pstmt.setString(2, classNO);
                pstmt.setString(3, credit);
                return pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } else return -1;
    }

    /**
     * 희망수업 목록에서 삭제함
     *
     * @param studentID
     * @param classNO
     * @return
     */
    public int deleteWantedClass(String studentID, String classNO) {
        String SQL = "delete from wanted_enrollment\n" +
                "where student_id = ? and class_no= ? ";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, classNO);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 수강취소 함수
     *
     * @param studentID
     * @param classNO
     * @return
     */
    public int unenrollClass(String studentID, String classNO) {
        String SQL = "delete from enrollment\n" +
                "where student_id = ? and class_no= ? ";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            pstmt.setString(2, classNO);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 시간표 출력함수
     * 특정 학생의 모든 수강신청 내역을 가져오고, Timeoperator class의 changeToPeriod() 를 통해,
     * 나타내기 쉬운 형태로 변환하여 list를 return 함.
     * 평일 19시 이후에 끝나는 수업과 토요일에 열리는 수업은 표현하지 않음.
     *
     * @param studentID
     * @return
     */
    public ArrayList<SearchingResult> getTimeTable(String studentID) {
        String SQL = "select CL.name, T.begin, T.end\n" +
                "from enrollment as E, Student as S, time as T, class as CL\n" +
                "where T.class_id = CL.class_id\n" +
                "and CL.class_no = E.class_no\n" +
                "and CL.opened = '2023'\n" +
                "and S.student_id = E.student_id\n" +
                "and S.student_id = ?";

        TimeOperator timeOperator = new TimeOperator();
        ArrayList<SearchingResult> list = new ArrayList();
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SearchingResult searchingResult = new SearchingResult();
                searchingResult.setCourseName(rs.getString(1));
                String result = timeOperator.changeToPeriod(rs.getString(2), rs.getString(2)).split(",")[1];
                if (Double.parseDouble(result.substring(3)) < 18) {
                    searchingResult.setTime(timeOperator.changeToPeriod(rs.getString(2), rs.getString(3)));
                    list.add(searchingResult);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

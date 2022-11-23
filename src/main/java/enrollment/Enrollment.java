package enrollment;

/**
 * 수강 신청에 관련된 객체
 */
public class Enrollment {
    private String classNO;
    private String studentID;
    private String credit;

    public String getClassNO() {
        return classNO;
    }

    public void setClassNO(String classNO) {
        this.classNO = classNO;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}

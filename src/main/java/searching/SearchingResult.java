package searching;

/**
 * 검색 결과를 보여주기 위한 class
 * 수강편람의 형식에 맞게 변수들을 정의함
 *수업번호, 학수번호, 교과목명, 교강사이름, 수업시간, 신청인원/수강정원, 강의실(건물+호수)
 *
 */

public class SearchingResult {
    private String classNO;
    private String courseID;
    private String courseName;
    private String credit;
    private String lecturerName;
    private String time;
    private String enrollmentCount;
    private String personMax;
    private String buildingName;

    public String getClassNO() {
        return classNO;
    }

    public void setClassNO(String classNO) {
        this.classNO = classNO;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(String enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public String getPersonMax() {
        return personMax;
    }

    public void setPersonMax(String personMax) {
        this.personMax = personMax;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}

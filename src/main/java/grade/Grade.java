package grade;

/**
 * 성적에 대한 class
 */
public class Grade {
    private String creditsID;
    private String studentID;
    private String courseID;
    private String year;
    private String grade;

    public String getCreditsID() {
        return creditsID;
    }

    public void setCreditsID(String creditsID) {
        this.creditsID = creditsID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
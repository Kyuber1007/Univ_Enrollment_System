package course_grade_list;

/**
 * OLAP 계산을 위해서 각 course 별로 평균 grade를 저장하는 객체.
 * 편의를 위해, 각 과목의 credit과 course 전체를 포함한 평균도 같이 담고 있음.
 */
public class CourseGrade {
    private String courseID;
    private int credit;
    private String grades;
    private int gradeNum;
    private double averageGrade;
    private double differenceAverageAndCourse;
    private double totalAverage;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public int getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(int gradeNum) {
        this.gradeNum = gradeNum;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public double getDifferenceAverageAndCourse() {
        return differenceAverageAndCourse;
    }

    public void setDifferenceAverageAndCourse(double differenceAverageAndCourse) {
        this.differenceAverageAndCourse = differenceAverageAndCourse;
    }

    public double getTotalAverage() {
        return totalAverage;
    }

    public void setTotalAverage(double totalAverage) {
        this.totalAverage = totalAverage;
    }
}
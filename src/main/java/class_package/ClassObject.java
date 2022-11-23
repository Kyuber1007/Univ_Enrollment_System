package class_package;

/**
 * 각 수업을 표현하기 위한 객체
 * class라는 이름으로 class를 생성할 수 없어서
 * ClassObject라는 이름으로 생성함.
 */
public class ClassObject {
    private String classID;
    private String classNO;
    private String courseID;
    private String name;
    private String majorID;
    private String year;
    private String credit;
    private String lecturerID;
    private String personMax;
    private String opened;
    private String roomID;

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajorID() {
        return majorID;
    }

    public void setMajorID(String majorID) {
        this.majorID = majorID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getPersonMax() {
        return personMax;
    }

    public void setPersonMax(String personMax) {
        this.personMax = personMax;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
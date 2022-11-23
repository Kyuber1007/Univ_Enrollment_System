<%@ page import="class_package.ClassDAO" %>
<%@ page import="admin.AdminDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>
<jsp:useBean id="course" class="course.Course" scope="page"/>
<jsp:useBean id="major" class="major.Major" scope="page"/>
<jsp:useBean id="lecturer" class="lecturer.Lecturer" scope="page"/>
<jsp:useBean id="classTime" class="classTime.ClassTime" scope="page"/>
<jsp:useBean id="room" class="room.Room" scope="page"/>

<jsp:setProperty name="course" property="courseID"/>
<jsp:setProperty name="classObject" property="classNO"/>
<jsp:setProperty name="classObject" property="classID"/>
<jsp:setProperty name="classObject" property="personMax"/>
<jsp:setProperty name="classObject" property="year"/>
<jsp:setProperty name="lecturer" property="lecturerID"/>
<jsp:setProperty name="major" property="majorID"/>
<jsp:setProperty name="classTime" property="timeID"/>
<jsp:setProperty name="classTime" property="begin"/>
<jsp:setProperty name="classTime" property="end"/>
<jsp:setProperty name="room" property="roomID"/>

<html>
<head>
    <title>수업 개설 액션</title>
</head>
<body>

<%
    AdminDAO adminDAO = new AdminDAO();
    PrintWriter script = response.getWriter();
    int result = adminDAO.createClass(course.getCourseID(), classObject.getClassNO(), classObject.getClassID(), classObject.getPersonMax(), classObject.getYear(), lecturer.getLecturerID(), major.getMajorID(), classTime.getTimeID(), classTime.getBegin(), classTime.getEnd(), room.getRoomID());
    if (result == 1) {
        script.println("<script>");
        script.println("alert('생성 성공')");
        script.println("location.href='adminMain.jsp'");
        script.println("</script>");
    } else if (result == 0) {
        script.println("<script>");
        script.println("alert('database 오류, class no이나 class id를 확인하세요')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -1) {
        script.println("<script>");
        script.println("alert('존재하지 않는 강의실 id')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -2) {
        script.println("<script>");
        script.println("alert('강의실 정원 초과 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -3) {
        script.println("<script>");
        script.println("alert('일요일 생성 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -4) {
        script.println("<script>");
        script.println("alert('course id 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -5) {
        script.println("<script>");
        script.println("alert('해당 시간대에 해당 강의실 사용 중 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    } else if (result == -6) {
        script.println("<script>");
        script.println("alert('해당 시간대에 교수 강의 중 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    }else if (result == -9) {
        script.println("<script>");
        script.println("alert('time table 오류')");
        script.println("history.go(-1)");
        script.println("</script>");
    }
%>
</body>
</html>

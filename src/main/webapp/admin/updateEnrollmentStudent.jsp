<%--
    수강허용 시 실행되는 action page
    추가 요청 결과에 따라 알람 표현.
--%>
<%@ page import="enrollment.EnrollmentDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="student" class="student.Student" scope="page"/>
<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>
<jsp:useBean id="course" class="course.Course" scope="page"/>

<jsp:setProperty name="course" property="courseID"/>
<jsp:setProperty name="course" property="credit"/>
<jsp:setProperty name="student" property="studentID"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
    <title>수강 허용</title>
</head>
<body>
<%
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    int result = enrollmentDAO.enrollClass(classObject.getClassNO(), student.getStudentID(), course.getCourseID(), course.getCredit());
    PrintWriter script = response.getWriter();
    if (result > 0) {
        script.println("<script>");
        script.println("alert('추가 성공.')");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    } else if (result == 0) {
        script.println("<script>");
        script.println("alert('database 오류.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }else if (result == -1) {
        script.println("<script>");
        script.println("alert('학점 초과.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }else if (result == -2) {
        script.println("<script>");
        script.println("alert('재수강 성적 오류.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }else if (result == -3) {
        script.println("<script>");
        script.println("alert('정원 초과.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }else if (result == -4) {
        script.println("<script>");
        script.println("alert('시간 겹침.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }else if (result == -5) {
        script.println("<script>");
        script.println("alert('이미 신청된 항목.')");
        script.println("history.back()");
        script.println("location.href='changeClass.jsp'");
        script.println("</script>");
    }
%>

</body>
</html>

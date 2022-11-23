<%--
    학생의 재적 정보를 변경할 때 불리는 action page
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="admin.AdminDAO" %>
<%@ page import="student.Student" %>
<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="student" class="student.Student" scope="page"/>
<jsp:useBean id="admin" class="admin.AdminDAO" scope="page"/>
<jsp:setProperty name="student" property="studentID"/>
<jsp:setProperty name="student" property="password"/>
<jsp:setProperty name="student" property="name"/>
<jsp:setProperty name="student" property="sex"/>
<jsp:setProperty name="student" property="majorID"/>
<jsp:setProperty name="student" property="lecturerID"/>
<jsp:setProperty name="student" property="year"/>
<jsp:setProperty name="student" property="status"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>학생 정보 변경 액션</title>
</head>
<body>
<%
    AdminDAO adminDAO = new AdminDAO();
    Student studentObject = new Student();
    studentObject.setStudentID(student.getStudentID());
    int result = adminDAO.changeStudent(student);

    if (result >= 0) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('변경!.')");
        script.println("location.href = 'showAllStudent.jsp'");
        script.println("</script>");
    } else {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('데이터베이스 오류가 발생했습니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>

</body>
</html>
<%--
    학생 로그인 action page
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="student.StudentDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="student" class="student.Student" scope="page"/>

<jsp:setProperty name="student" property="studentID"/>
<jsp:setProperty name="student" property="password"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>학생 로그인 액션</title>
</head>
<body>
<%
    String studentID = null;
    if (session.getAttribute("studentID") != null) {
        studentID = (String) session.getAttribute("studentID");
    }
    if (studentID != null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 로그인이 되어있습니다.')");
        script.println("location.href = 'studentMain.jsp'");
        script.println("</script>");
    }

    StudentDAO studentDAO = new StudentDAO();

    int result = studentDAO.login(student.getStudentID(), student.getPassword());

    if (result == 1) {
        session.setAttribute("studentID", student.getStudentID());
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인 성공!.')");
        script.println("location.href = 'studentMain.jsp'");
        script.println("</script>");
    } else if (result == 0) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('비밀번호가 틀립니다.')");
        script.println("history.back()");
        script.println("</script>");
    } else if (result == -1) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('존재하지 않는 학번입니다.')");
        script.println("history.back()");
        script.println("</script>");
    } else if (result == -2) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('데이터베이스 오류가 발생했습니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>

</body>
</html>
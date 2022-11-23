<%--
    학생 정보 변경 시 실행되는 action page
    현재는 비밀번호만 수정할 수 있음.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="student.StudentDAO" %>
<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="student" class="student.Student" scope="page"/>
<jsp:setProperty name="student" property="password"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>정보 변경 액션</title>
</head>
<body>
<%
    StudentDAO studentDAO = new StudentDAO();
    String studentID = (String) session.getAttribute("studentID");
    int result = studentDAO.changePassword(studentID, student.getPassword());
    if (result >= 0) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('변경!.')");
        script.println("location.href = 'changeInfo.jsp'");
        script.println("</script>");
    } else {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('데이터베이스 오류가 발생했습니다.')");
        script.println("history.back()");
        script.println("location.href = 'changeInfo.jsp'");
        script.println("</script>");
    }
%>

</body>
</html>
<%--
    희망수업 삭제시 실행되는 page
--%>

<%@ page import="enrollment.EnrollmentDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
    <title>희망 삭제 액션</title>
</head>
<body>
<%
    String studentID = (String) session.getAttribute("studentID");
    String[] classNO = request.getParameterValues("classNO");
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    PrintWriter script = response.getWriter();
    int result = 0;
    for (int i = 0; i < classNO.length; i++) {
        result = enrollmentDAO.deleteWantedClass(studentID, classNO[i]);
        if (result > 0) {
            script.println("<script>");
            script.println("alert('삭제 성공.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else {
            script.println("<script>");
            script.println("alert('삭제 실패.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        }
    }
%>
</body>
</html>

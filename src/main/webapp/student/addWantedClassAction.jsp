<%--
    희망과목 추가 action
    희망과목를 추가하는 함수를 호출하고 결과에 따라 return함.
--%>

<%@ page import="enrollment.EnrollmentDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
    <title>희망 추가 액션</title>
</head>
<body>
<%
    String studentID = (String) session.getAttribute("studentID");
    String[] classNO = request.getParameterValues("classNO");
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    PrintWriter script = response.getWriter();
    int result = 0;
    if (classNO == null) {
        script.println("<script>");
        script.println("alert('하나는 선택하세요!')");
        script.println("location.href='studentMain.jsp'");
        script.println("</script>");
    }

    for (int i = 0; i < classNO.length; i++) {
        result = enrollmentDAO.addWantedClass(studentID, classNO[i]);
        if (result > 0) {
            script.println("<script>");
            script.println("alert('추가 성공.')");
            script.println("location.href='studentMain.jsp'");
            script.println("</script>");
        } else if (result == 0) {
            script.println("<script>");
            script.println("alert('database 오류.')");
            script.println("location.href='studentMain.jsp'");
            script.println("</script>");
        } else if (result == -1) {
            script.println("<script>");
            script.println("alert('이미 희망 신청한 과목 존재  .')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        }
    }
%>
</body>
</html>

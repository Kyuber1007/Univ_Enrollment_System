<%--
    수강신청시 실행되는 page
    결과에 따른 알림.
--%>

<%@ page import="enrollment.EnrollmentDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>

<html>
<head>
    <title>수강 신청 액션</title>
</head>
<body>
<%
    String studentID = (String) session.getAttribute("studentID");
    String[] classNO = request.getParameterValues("classNO");
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    PrintWriter script = response.getWriter();
    if (classNO == null) {
        script.println("<script>");
        script.println("alert('하나는 선택하세요!')");
        script.println("location.href='showWanted.jsp'");
        script.println("</script>");
    }
    for (int i = 0; i < classNO.length; i++) {
        int result = enrollmentDAO.enrollClass(studentID, classNO[i]);
        if (result > 0) {
            script.println("<script>");
            script.println("alert('추가 성공.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else if (result == 0) {
            script.println("<script>");
            script.println("alert('database 오류.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");

        } else if (result == -5) {
            script.println("<script>");
            script.println("alert('이미 신청된 항목.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else if (result == -3) {
            script.println("<script>");
            script.println("alert('정원 초과.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else if (result == -2) {
            script.println("<script>");
            script.println("alert('재수강 성적 오류.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else if (result == -1) {
            script.println("<script>");
            script.println("alert('학점 초과.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        } else if (result == -4) {
            script.println("<script>");
            script.println("alert('시간 겹침.')");
            script.println("location.href='showWanted.jsp'");
            script.println("</script>");
        }
    }

%>
</body>
</html>

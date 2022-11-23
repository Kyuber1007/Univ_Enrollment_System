<%--
    수업 정원 변경시 실행되는 action page
    정원 변경 함수를 호출함.
--%>
<%@ page import="class_package.ClassDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>

<jsp:setProperty name="classObject" property="personMax"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
    <title>정원 늘리기 action</title>
</head>
<body>
<%
    ClassDAO classDAO = new ClassDAO();
    int result = classDAO.editPersonMax(classObject.getClassNO(), classObject.getPersonMax());
    PrintWriter script = response.getWriter();
    if (result == 1) {
        script.println("<script>");
        script.println("alert('변경!.')");
        script.println("history.back()");
        script.println("</script>");
    }
    if (result == 0) {
        script.println("<script>");
        script.println("alert('강의실 정원이 부족하거나 0 이하의 값을 입력하는 문제 발생')");
        script.println("history.back()");
        script.println("</script>");
    }
    if (result == -1) {
        script.println("<script>");
        script.println("alert('database 오류.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>


</body>
</html>

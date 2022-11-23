<%--
    폐강할 때 호출하는 page
--%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="admin.AdminDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
    <title>과목 삭제</title>
</head>
<body>
<%
    AdminDAO adminDAO = new AdminDAO();
    String classNO = classObject.getClassNO();
    int result = adminDAO.deleteClass(classNO);
    PrintWriter script = response.getWriter();
    if (result == 1) {
        script.println("<script>");
        script.println("alert('삭제완료!')");
        script.println("history.back()");
        script.println("</script>");
    } else {
        script.println("<script>");
        script.println("alert('삭제실패!')");
        script.println("history.back()");
        script.println("</script>");
    }
%>

</body>
</html>

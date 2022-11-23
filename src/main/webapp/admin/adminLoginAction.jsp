<%--
    amdin이 로그인 버튼을 눌렀을 때 실행되는 page
    이미 로그인 되어 있는지, 혹은, 비밀번호, 관리자 코드를 검사하는 함수를 호출하여
    로그인 성공 여부를 결정함
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="admin.AdminDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="admin" class="admin.Admin" scope="page" />
<jsp:setProperty name="admin" property="adminID" />
<jsp:setProperty name="admin" property="password"/>
<jsp:setProperty name="admin" property="adminCode"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>학생 로그인 액션</title>
</head>
<body>
<%
    String adminID = null;
    if(session.getAttribute("adminID") != null)
    {
        adminID = (String) session.getAttribute("adminID");
    }
    if(adminID != null)
    {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 로그인이 되어있습니다.')");
        script.println("location.href = 'adminMain.jsp'");
        script.println("</script>");
    }

    AdminDAO adminDAO = new AdminDAO();

    int result = adminDAO.login(admin.getAdminID(), admin.getPassword(), admin.getAdminCode());

    if(result == 1)
    {
        session.setAttribute("adminID", admin.getAdminID());
        System.out.println("로그인 성공");
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인 성공.')");
        script.println("location.href = 'adminMain.jsp'");
        script.println("</script>");
    }
    else if(result == 0)
    {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('비밀번호 혹은 관리자 코드가 틀립니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
    else if(result == -1)
    {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('존재하지 않는 관리자 아이디입니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
    else if(result == -2)
    {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('데이터베이스 오류가 발생했습니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>메인페이지</title>
</head>
<body>
<%
    String studentID = null;
    String adminID = null;

//    user(student)와 admin 중 어떤 유형으로 사용하고자 하는 지를 고려해야함, 버튼을 두개로 만듦.
//    또한, 관리자와 학생을 구분하기 위해 userType String 을 활용함
    if (session.getAttribute("studentID") != null) {
        studentID = (String) session.getAttribute("studentID");
    }
    if (session.getAttribute("adminID") != null) {
        adminID = (String) session.getAttribute("adminID");
    }
%>
<h3 style="test-align: center;">메인 페이지</h3>
<%
    if (studentID == null && adminID == null) {
%>
<div>
    <button type="button" class="navyBtn" onClick="location.href='student/studentLogin.jsp'">학생 로그인</button>
    <button type="button" class="navyBtn" onClick="location.href='admin/adminLogin.jsp'">관리자 로그인</button>
</div>
<%
} else {
%>
<button type="button" class="navyBtn" onClick="location.href='logoutAction.jsp'">로그아웃</button>
<%
    }
%>
</body>
</html>
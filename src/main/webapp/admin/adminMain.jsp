<%--
    관리자 main page
    모든 학생 정보
    OLAP 결과
    로그아웃 버튼이 있으며
    설강, 폐강을 할 수 있고

    searching을 할 수 있음
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>관리자 메인 페이지</title>
</head>
<body>
<%
    String adminID = null;

    if (session.getAttribute("adminID") != null) {
        adminID = (String) session.getAttribute("adminID");
    }
%>
<h3 style="test-align: center;">관리자 메인 페이지</h3>
<button type="button" class="navyBtn" onClick="location.href='showAllStudent.jsp'">모든 학생 정보</button>
<button type="button" class="navyBtn" onClick="location.href='calculateOLAP.jsp'">OLAP 기능</button>
<button type="button" class="navyBtn" onClick="location.href='../logoutAction.jsp'">로그아웃</button>
<div>
    <br>
    <form method="post" action="deleteClass.jsp">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="수업번호" name="classNO" maxlength="20"
                   required>
            <input type="submit" class="btn form-control" value="폐강">
            <button type="button" class="navyBtm" onclick="location.href='createClass.jsp'">설강</button>
        </div>
    </form>
    <jsp:include page="../searching/searchingClassForAdmin.jsp" flush="false"/>
</div>
</body>
</html>
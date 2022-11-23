<%--
    학생 main page
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>사용자 메인 페이지</title>
</head>
<body>
<h3 style="test-align: center;">사용자 메인 페이지 (<%=session.getAttribute("studentID")%>) </h3>

<button type="button" class="navyBtn" onClick="location.href='showWanted.jsp'">수강 신청 관련 정보</button>
<button type="button" class="navyBtn" onClick="location.href='changeInfo.jsp'">정보</button>
<button type="button" class="navyBtn" onClick="location.href='../logoutAction.jsp'">로그아웃</button>

<form method="post" action="enrollClassAction.jsp">
    <br>
    <input type="text" class="form-control" placeholder="수업번호" name="classNO" maxlength="20" required>
    <input type="submit" class="btn btn-primary form-control" value="수강신청">
</form>
<div class="form-group">
    <jsp:include page="../searching/searchingClassForStudent.jsp" flush="false"/>
    <%--    <input type="text" class="form-control" placeholder="수업번호" name="classID" maxlength="20">--%>
    <%--    <button type="button" class="navyBtn" onClick="location.href='searchCourse.jsp'">검색</button>--%>
    <%--    <input type="text" class="form-control" placeholder="학수번호" name="courseID" maxlength="20">--%>
    <%--    <button type="button" class="navyBtn" onClick="location.href='searchCourse.jsp'">검색</button>--%>
    <%--    <input type="text" class="form-control" placeholder="교과목명" name="courseName" maxlength="20">--%>
    <%--    <button type="button" class="navyBtn" onClick="location.href='searchCourse.jsp'">검색</button>--%>
</div>
</body>
</html>
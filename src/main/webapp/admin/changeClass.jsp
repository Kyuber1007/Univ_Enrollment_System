<%--
    수업 정보 변경을 위해 만들어진 page
    1. 기본적인 수업 정보를 보여줌
    2. 수업 정원 조절 부분만 열어둠. 수업 정원에 입력된 값이 바꾸고 정원 늘리기 버튼을 클릭하면 changeClassPersonMax.jsp를 호출함.
        - 성공 여부에 따라 알림창을 띄움.
    3. 수강허용을 통해, 특정 학번의 학생을 수강허용해줌.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="searching.SearchingResult" %>
<%@ page import="searching.SearchingDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>

<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="student" class="student.Student" scope="page"/>
<jsp:useBean id="searchingResult" class="searching.SearchingResult" scope="page"/>
<jsp:setProperty name="searchingResult" property="classNO"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>과목(수업) 변경</title>
</head>
<body>
<%
    if (searchingResult.getClassNO() == null) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('하나라도 선택하세요.')");
        script.println("history.back()");
        script.println("</script>");
    }
    SearchingDAO searchingDAO = new SearchingDAO();
    ArrayList<SearchingResult> searchingResultArrayList = searchingDAO.searchByClassNO(searchingResult.getClassNO());
    searchingResult = searchingResultArrayList.get(0);
%>
<div>
    <h3>과목 정보 수정</h3>
    <div>
        <button type="button" class="navyBtn" onclick="location.href='adminMain.jsp'">관리자 메인으로</button>
    </div>
    <br>
    <div>
        <form method='post' action='changeClassPersonMax.jsp'>
            수업번호: <input type="text" class="form-control" name="classNO"
                         value=<%=searchingResult.getClassNO()%> readonly><br>
            학수번호: <%=searchingResult.getCourseID()%><br>
            교과목명: <%=searchingResult.getCourseName()%><br>
            학점: <%=searchingResult.getCredit()%><br>
            교강사이름: <%=searchingResult.getLecturerName()%><br>
            수업시간: <%=searchingResult.getTime()%><br>
            신청인원: <%=searchingResult.getEnrollmentCount()%><br>
            정원(수정하려면 다른 숫자 입력):<input type="text" class="form-control" name="personMax"
                                      value=<%=searchingResult.getPersonMax()%>><br>
            강의실: <%=searchingResult.getBuildingName()%><br> <br>
            <input type="submit" class="btn btn-primary form-control" value="정원 늘리기">
        </form>
    </div>
    <br>
    <hr>
    <div>
        <h4>수강 허용 학번 입력</h4>
        <form method="post" action='updateEnrollmentStudent.jsp'>
            <div class="form-group">
                수업번호: <input type="text" class="form-control" name="classNO"
                             value=<%=searchingResult.getClassNO()%> readonly><br>
                학수번호: <input type="text" class="form-control" name="courseID"
                             value=<%=searchingResult.getCourseID()%> readonly><br>
                학점: <input type="text" class="form-control" name="credit"
                           value=<%=searchingResult.getCredit()%> readonly><br>
                <input type="text" class="form-control" placeholder="학번 입력" name="studentID" maxlength="20" required>
                <input type="submit" class="btn btn-primary form-control" value="수강 허용">
            </div>
        </form>
    </div>

</div>
<script>
    function cancelChange() {
        alert('변경 취소.');
        history.back();
    }
</script>
</body>
</html>
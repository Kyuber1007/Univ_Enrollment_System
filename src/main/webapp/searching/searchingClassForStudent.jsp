<%--
    학생의 수강 편람 부분
    기본적으로 모든 수업들을 나열해 놓음.
    class table을 기준으로 2023년에 설강된 수업을 나열해놓고,
    교수id와 전공id를 각각 교수명과 전공명으로 변경함
--%>

<%@ page import="searching.SearchingResult" %>
<%@ page import="searching.SearchingDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>검색</title>
</head>
<body>
<%
    String studendID = null;
    if (session.getAttribute("studentID") != null) {
        studendID = (String) session.getAttribute("studentID");
    }
%>
<hr>
<h4 style="test-align: center;">검색</h4>
<div class="form-group">
    <form method="post" action="../searching/searchingClassForStudentAction.jsp">
        <p>한가지만 입력하고 검색하세요!</p>
        <input type="text" class="form-control" placeholder="수업번호" name="classNO" maxlength="20">
        <input type="text" class="form-control" placeholder="학수번호" name="courseID" maxlength="20">
        <input type="text" class="form-control" placeholder="교과목명" name="name" maxlength="20">
        <input type="submit" class="btn btn-primary form-control" value="검색">
    </form>

    <br>
    <%--    모든 수업들을 나열함  --%>
    <div class="container">
        <div class="row">
            <form method="post" action="addWantedClassAction.jsp">
                <input type="submit" class="btn btn-primary form-control" value="희망 신청 하기">
                <br>
                <table class="table" id="classTable" style="text-align: center; border: 10px solid #dddddd" ;>
                    <thead>
                    <tr>
                        <th style="text-align: center; "></th>
                        <th style="text-align: center; "> 희망</th>
                        <th style="text-align: center; "> 수업번호</th>
                        <th style="text-align: center; "> 학수번호</th>
                        <th style="text-align: center; "> 교과목명</th>
                        <th style="text-align: center; "> 학점</th>
                        <th style="text-align: center; "> 교강사이름</th>
                        <th style="text-align: center; "> 수업시간</th>
                        <th style="text-align: center; "> 신청인원/수강정원</th>
                        <th style="text-align: center; "> 강의실</th>
                        <th style="text-align: center;"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        SearchingDAO searchingDAO = new SearchingDAO();
                        ArrayList<SearchingResult> list = searchingDAO.showAllClass();
                        for (int i = 0; i < list.size(); i++) {
                    %>
                    <tr>
                        <td><%= (i + 1)%>
                        </td>
                        <td>
                            <input id="" type="checkbox" name="classNO" value=<%=list.get(i).getClassNO()%>>
                        </td>
                        <td><%= list.get(i).getClassNO()%>
                        </td>
                        <td><%= list.get(i).getCourseID()%>
                        </td>
                        <td><%= list.get(i).getCourseName()%>
                        </td>
                        <td><%= list.get(i).getCredit()%>
                        </td>
                        <td><%= list.get(i).getLecturerName()%>
                        </td>
                        <td><%= list.get(i).getTime()%>
                        </td>
                        <td><%= list.get(i).getEnrollmentCount() + "/" + list.get(i).getPersonMax()%>
                        </td>
                        <td><%= list.get(i).getBuildingName()%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>

</body>
</html>
<%--
    학생 수강신청 내역 보여줌
    체크 박스 선택 후 삭제 버튼 클릭시 신청 취소
--%>
<%@ page import="searching.SearchingDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="searching.SearchingResult" %>
<%@ page import="enrollment.EnrollmentDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>수강 신청 내역</title>
</head>
<body>
<div>
    <button type="button" class="navyBtn" onclick="location.href='studentMain.jsp'">학생 메인으로</button>
</div>
<h3>신청 내역</h3>
<div class="container">
    <div class="row">
        <form method="post" action="unenrollClassAction.jsp">
            <input type="submit" class="btn btn-primary form-control" value="신청 삭제">
            <button type="button" class="navyBtn" onclick="location.href='timeTable.jsp'">시간표</button>
            <br>
            <table class="table" id="classTable" style="text-align: center; border: 10px solid #dddddd" ;>
                <thead>
                <tr>
                    <th style="text-align: center; "></th>
                    <th style="text-align: center; "></th>
                    <th style="text-align: center; "> 수업번호</th>
                    <th style="text-align: center; "> 학수번호</th>
                    <th style="text-align: center; "> 교과목명</th>
                    <th style="text-align: center; "> 학점</th>
                    <th style="text-align: center; "> 교강사이름</th>
                    <th style="text-align: center; "> 수업시간</th>
                    <th style="text-align: center; "> 신청인원/수강정원</th>
                    <th style="text-align: center; "> 강의실</th>
                    <th style="text-align: center;"> 재수강</th>
                </tr>
                </thead>
                <tbody>
                <%
                    String studentID = (String) session.getAttribute("studentID");
                    SearchingDAO searchingDAO = new SearchingDAO();
                    ArrayList<SearchingResult> list = searchingDAO.showStudentClass(studentID);
                    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
                    boolean reEnroll;
                    String s = "X";
                    for (int i = 0; i < list.size(); i++) {
                        reEnroll = enrollmentDAO.checkReEnrollmentBoolean(studentID, list.get(i).getCourseID());
                        if (reEnroll){
                            s = "O";
                        }
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
                    <td><%= s%>
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
<%

%>
</body>
</html>

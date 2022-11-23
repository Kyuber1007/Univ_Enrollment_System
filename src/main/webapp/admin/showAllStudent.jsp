<%--
    모든 학생들의 정보를 보여주기 위한 Page
    학생들의 id, 이름, 성별, 전공명, 지도교수, 학년, 학점, 생태를 보여줌
    학번을 입력하고 변경을 누르거나,
    학번을 입력하고 시간표보기를 누르면
    보다 자세한 정보를 수정 및 파악할 수 있게 구현.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="admin.AdminDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="student.StudentInfo" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>모든 학생 정보</title>
</head>
<body>
<h3 style="test-align: center;">모든 학생 정보</h3>
<div>
    <button type="button" class="navyBtn" onclick="location.href='adminMain.jsp'">관리자 메인으로</button>
</div>
<div>
    <form method="post" action="changeStudent.jsp">
        <br>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="변경할 학번 입력" name="studentID" maxlength="20" required>
            <input type="submit" class="btn form-control" value="변경">
        </div>
    </form>
</div>
<div class="container">
    <div class="row">
        <form method="post" action="../student/timeTable.jsp">
            <br>
            <input type="submit" class="btn-right btn-primary form-control" value="선택학생 시간표 조회">
            <table class="table" width="750" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;"></th>
                <th style="text-align: center;"> ID</th>
                <th style="text-align: center;"> 이름</th>
                <th style="text-align: center;"> 성별</th>
                <th style="text-align: center;"> 전공명</th>
                <th style="text-align: center;"> 지도교수</th>
                <th style="text-align: center;"> 학년</th>
                <th style="text-align: center;"> 학점</th>
                <th style="text-align: center;"> 상태</th>
                <th style="text-align: center;">시간표</th>
            </tr>
            </thead>
            <tbody>
            <%
                AdminDAO adminDAO = new AdminDAO();
                ArrayList<StudentInfo> list = adminDAO.showAllStudent();
                for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%= (i + 1)%>
                </td>
                <td><%=list.get(i).getStudentID()%>
                </td>
                <td><%= list.get(i).getName()%>
                </td>
                <td><%= list.get(i).getSex()%>
                </td>
                <td><%= list.get(i).getMajorName()%>
                </td>
                <td><%= list.get(i).getLecturerName()%>
                </td>
                <td><%= list.get(i).getYear()%>
                </td>
                <td><%= list.get(i).getGrade()%>
                </td>
                <td><%= list.get(i).getStatus()%>
                </td>
                <td>
                    <input id="" type="checkbox" name="studentID" value=<%=list.get(i).getStudentID()%>
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

<script src="https://code.jquery.com/jquery-3.1.1.min.js">
</script>
<script src="../js/bootstrap.js"></script>

</body>
</html>
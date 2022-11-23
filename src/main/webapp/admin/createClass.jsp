<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="admin.AdminDAO" %>
<%@ page import="lecturer.Lecturer" %>
<%@ page import="lecturer.LecturerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="major.Major" %>
<%@ page import="major.MajorDAO" %>

<html>
<head>
    <title>과목 생성</title>
    <style>

        div.left {
            width: 25%;
            float: left;
        }

        div.right {
            width: 75%;
            float: right;
        }
    </style>
</head>
<button type="button" class="navyBtn" onclick="history.back()">이전 화면</button>
<body>
<%
%>
<form method="post" action="createClassAction.jsp">
    <br>
    <div class="form-group">
        <input type="text" class="form-control" placeholder="생성할 class_id 입력" name="classID" maxlength="20"
               required><br>
        <input type="text" class="form-control" placeholder="생성할 class_no 입력" name="classNO" maxlength="20"
               required> <br>
        <input type="text" class="form-control" placeholder="생성할 course_id 입력" name="courseID" maxlength="20"
               required><br>
        <input type="text" class="form-control" placeholder="전공 id 입력" name="majorID" maxlength="20" required><br>
        <input type="text" class="form-control" placeholder="학년 입력" name="year" maxlength="20" required><br>
        <input type="text" class="form-control" placeholder="교강사 id 입력" name="lecturerID" maxlength="20" required><br>
        <input type="text" class="form-control" placeholder="정원 입력" name="personMax" maxlength="20" required><br>
        <input type="text" class="form-control" placeholder="time id 입력" name="timeID" maxlength="100" required><br>
        <input type="text" class="form-control" placeholder="시작 시간 입력" name="begin" maxlength="100" required><br>
        <input type="text" class="form-control" placeholder="종료 시간 입력" name="end" maxlength="100" required><br>
        <input type="text" class="form-control" placeholder="강의실 id 입력" name="roomID" maxlength="20" required><br>
        <input type="submit" class="btn form-control" value="과목 설강">
    </div>
</form>

<div class="left">
    <div class="row">
        <br>
        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;"> ID</th>
                <th style="text-align: center;"> 이름</th>
                <th style="text-align: center;"> 전공 id</th>
            </tr>
            </thead>
            <tbody>
            <%
                LecturerDAO lecturerDAO = new LecturerDAO();
                ArrayList<Lecturer> list = lecturerDAO.showAllLecturer();
                for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%=list.get(i).getLecturerID()%>
                </td>
                <td><%= list.get(i).getName()%>
                </td>
                <td><%= list.get(i).getMajorID()%>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<div class="right">
    <div class="row">
        <br>
        <table class="table" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;"> ID</th>
                <th style="text-align: center;"> 이름</th>
            </tr>
            </thead>
            <tbody>
            <%
                MajorDAO majorDAO = new MajorDAO();
                ArrayList<Major> list2 = majorDAO.showAllMajor();
                for (int i = 0; i < list2.size(); i++) {
            %>
            <tr>
                <td><%=list2.get(i).getMajorID()%>
                </td>
                <td><%= list2.get(i).getName()%>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

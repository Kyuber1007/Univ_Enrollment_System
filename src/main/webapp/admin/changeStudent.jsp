<%--
    학생 정보를 조회하고, 변경할 때 보여지는 page
    학생 재정 상태를 변경할 수 있음.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="student.StudentDAO" %>
<%@ page import="student.Student" %>
<%@ page import="major.MajorDAO" %>
<%@ page import="lecturer.LecturerDAO" %>

<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="student" class="student.Student" scope="page"/>
<jsp:setProperty name="student" property="studentID"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <title>학생 정보 수정</title>
</head>
<body>
<%
    StudentDAO studentDAO = new StudentDAO();
    String studentID = student.getStudentID();
    MajorDAO majorDAO = new MajorDAO();
    LecturerDAO lecturerDAO = new LecturerDAO();
    Student studentObject = studentDAO.getInfo(studentID);
%>
<div>
    <h3>학생 정보 수정</h3>
    <div>
        <button type="button" class="navyBtn" onclick="history.back()">이전 화면</button>
    </div>
    <form method='post' action='changeStudentAction.jsp'>
        <br>
        <div class="form-group">
            학번: <input type="text" class="form-control" name="studentID"
                       value=<%=studentObject.getStudentID()%> readonly><br>
            비밀번호: <input type="text" class="form-control" name="password" value=<%=studentObject.getPassword()%>><br>
            이름: <input type="text" class="form-control" name="name" value=<%=studentObject.getName()%>><br>
            성별: <input type="text" class="form-control" name="sex" value=<%=studentObject.getSex()%>><br>
            전공: <input type="text" class="form-control" name="majorID"
                       value=<%=studentObject.getMajorID()%>> <%=majorDAO.findMajorNameByMajorID(studentObject.getMajorID())%><br>
            지도교수: <input type="text" class="form-control" name="lecturerID"
            value=<%=studentObject.getLecturerID()%>><%=lecturerDAO.findLecturerNameByLecturerID(studentObject.getLecturerID())%><br>
            학년: <input type="text" class="form-control" name="year" value=<%=studentObject.getYear()%>><br>
            <div>상태:
                <%
                    if (studentObject.getStatus().equals("재학")) {
                %>
                <input type="radio" class="form-control" name="status" value="재학" checked>재학
                <input type="radio" class="form-control" name="status" value="휴학">휴학
                <input type="radio" class="form-control" name="status" value="자퇴">자퇴
                <%
                } else if (studentObject.getStatus().equals("휴학")) {
                %> <input type="radio" class="form-control" name="status" value="재학" required>재학
                <input type="radio" class="form-control" name="status" value="휴학" checked>휴학
                <input type="radio" class="form-control" name="status" value="자퇴">자퇴
                <%
                } else if (studentObject.getStatus().equals("자퇴")) {
                %>
                <input type="radio" class="form-control" name="status" value="재학" required>재학
                <input type="radio" class="form-control" name="status" value="휴학">휴학
                <input type="radio" class="form-control" name="status" value="자퇴" checked>자퇴
                <%
                    }
                %>
            </div>
            <input type="submit" class="btn btn-primary form-control" value="수정">
            <input type='button' value='취소' onClick="cancelChange()">
        </div>
    </form>

</div>
<script>
    function cancelChange() {
        alert('변경 취소.');
        history.back();
    }
</script>
</body>
</html>
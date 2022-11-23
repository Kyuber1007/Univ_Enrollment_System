<%--
    학생이 본인 정보를 조회하고
    현재는 비밀번호를 바꿀 수 있게 한 page
    추가적으로 학생에게 권한을 더 부여할 경우, <form> 부분을 수정하면 됨
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
    String studentID = (String) session.getAttribute("studentID");
    Student studentObject = new Student();
    MajorDAO majorDAO = new MajorDAO();
    LecturerDAO lecturerDAO = new LecturerDAO();
    studentObject = studentDAO.getInfo(studentID);
%>
<div>
    <h3>학생 정보 수정</h3>
    <button type="button" class="navyBtn" onclick="history.back()">이전 화면</button>

    <form method='post' action='changeInfoAction.jsp'><br>
        <div class="form-group">
            학번: <%=studentObject.getStudentID()%><br>
            비밀번호: <input type="text" class="form-control" name="password" value=<%=studentObject.getPassword()%>><br>
            이름: <%=studentObject.getName()%><br>
            성별: <%=studentObject.getSex()%><br>
            전공: <%=majorDAO.findMajorNameByMajorID(studentObject.getMajorID())%><br>
            지도교수: <%=lecturerDAO.findLecturerNameByLecturerID(studentObject.getLecturerID())%><br>
            상태: <%=studentObject.getStatus()%><br>
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
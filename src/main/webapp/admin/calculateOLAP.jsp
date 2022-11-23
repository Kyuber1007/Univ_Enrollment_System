<%--
    OLAP 연산을 진행하는 함수를 호출하고, 결과를 출력함
--%>
<%@ page import="admin.AdminDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="course_grade_list.CourseGrade" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OLAP 계산</title>
</head>
<body>
<%
    AdminDAO adminDAO = new AdminDAO();

    ArrayList<CourseGrade> list = new ArrayList();

    if (adminDAO.calculateOLAP() == 1) {
        list = adminDAO.showOLAP();
    } else {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('데이터베이스 오류가 발생했습니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>
<button type="button" class="navyBtn" onclick="history.back()">이전 화면</button>
<div class="container">
    <div class="row">
        <br>
        <table class="table" id="classTable" style="text-align: center; border: 10px solid #dddddd" ;>
            <thead>
            <tr>
                <th style="text-align: center; "></th>
                <th style="text-align: center; "> 학수번호</th>
                <th style="text-align: center; "> 과목평균</th>
                <th style="text-align: center; "> (전체평균 - 과목평균)</th>
                <th style="text-align: center; "> 전체평균</th>
                <th style="text-align: center;"></th>
            </tr>
            </thead>
            <tbody>
            <%
                for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%= (i + 1)%>
                </td>
                <td><%= list.get(i).getCourseID()%>
                </td>
                <td><%= String.format("%.1f",list.get(i).getAverageGrade())%>
                </td>
                <td><%= String.format("%.3f", list.get(i).getDifferenceAverageAndCourse()) %>
                </td>
                <td><%= String.format("%.3f", list.get(i).getTotalAverage())%>
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


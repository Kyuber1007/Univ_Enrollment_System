<%--
    시간표 구현
--%>

<%@ page import="enrollment.EnrollmentDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="searching.SearchingResult" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="student" class="student.Student"/>
<jsp:setProperty name="student" property="studentID"/>
<html>
<head>
    <title>학생 시간표</title>
</head>
<body>
<%!
    private String temTimeString;
    private double temTime;
%>
<%
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    String studentID = (String) session.getAttribute("studentID");
    if (studentID == null) {
        studentID = student.getStudentID();
    }

    ArrayList<SearchingResult> list = new ArrayList<>();
    System.out.println(studentID);
    list = enrollmentDAO.getTimeTable(studentID);
%>
<div class="container">
    <button type="button" class="navyBtn" onclick="history.back()">이전 화면</button>
    <div class="row"><br>
        <br>
        <table class="table table-bordered" id="our_table" width="80%" width="400" border="1px" solid="#FFFFFF">
            <thead class="thead-dark">
            <tr>
                <th scope="col">TIME</th>
                <th scope="col" width="20%">MON</th>
                <th scope="col" width="20%">TUE</th>
                <th scope="col" width="20%">WED</th>
                <th scope="col" width="20%">THU</th>
                <th scope="col" width="20%">FRI</th>
            </tr>
            </thead>
            <tbody>
            <%
                int size = -1;
                boolean flag = false;
                int count = -1;
                size = list.size();

                for (int time = 0; time < 20; time++) {
            %>
            <tr>
                <th>
                    <%
                        if (time % 2 == 0) {
                            temTime = time / 2 + 9;
                            temTimeString = time / 2 + 9 + ":00";
                        } else {
                            temTime = time / 2.0 + 9;
                            temTimeString = (time - 1) / 2 + 9 + ":30";
                        }
                    %><%=temTimeString%>
                </th>
                <%
                    for (int i = 0; i < 5; i++) {
                        count = 0;
                        while (count < size) {
                            SearchingResult searchingResult = list.get(count);
                            String[] searchingResultTimes = searchingResult.getTime().split(",");
                            String begin = searchingResultTimes[0].substring(3);
                            String end = searchingResultTimes[1].substring(3);

                            if (Integer.parseInt(searchingResultTimes[0].substring(0, 2)) == i + 1) {
                                if (Double.parseDouble(begin) <= temTime && Double.parseDouble(end) > temTime) {
                                    System.out.print(i + 1);
                                    System.out.print(searchingResult.getCourseName());
                                    System.out.println(searchingResult.getTime());
                %>
                <td style="background-color: rgba(101,227,72,0.54); text-align: center">
                    <%=searchingResult.getCourseName()%>
                </td>
                <%
                    flag = true;
                } else {
                %>
                <%
                            }
                        }
                        count++;
                    }
                    if (!flag) {
                %>
                <td>
                </td>
                <%
                        }
                        flag = false;
                    }
                %>
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
<%--
    admin에서 검색을 할 때, 결과를 보여주는 page
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="searching.SearchingResult" %>
<%@ page import="searching.SearchingDAO" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="course" class="course.Course" scope="page"/>
<jsp:useBean id="classObject" class="class_package.ClassObject" scope="page"/>

<jsp:setProperty name="course" property="name"/>
<jsp:setProperty name="course" property="courseID"/>
<jsp:setProperty name="classObject" property="classNO"/>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
  <title>검색 액션</title>
</head>
<body>
<%
  ArrayList<SearchingResult> list = new ArrayList<SearchingResult>();
  SearchingDAO searchingDAO = new SearchingDAO();
  System.out.println("10009");

  System.out.println(classObject.getClassNO());
  if (classObject.getClassNO() != null) {
    list = searchingDAO.searchByClassNO(classObject.getClassNO());
  } else if (course.getCourseID() != null) {
    list = searchingDAO.searchByCourseID(course.getCourseID());
  } else if (course.getName() != null) {
    System.out.println("course Name: " + course.getName() + "으로 검색");
    list = searchingDAO.searchByCourseName(course.getName());
  }
%>


<div class="container">
  <div class="row">
    <form method="post" action="../admin/changeClass.jsp">
      <button type="button" class="navyBtn" onclick="location.href='../admin/adminMain.jsp'">관리자 메인으로</button>
      <input type="submit" class="btn btn-primary form-control" value="과목변경하기">
      <br>
      <br>
      <table class="table" id="classTable" style="text-align: center; border: 10px solid #dddddd" ;>
        <thead>
        <tr>
          <th style="text-align: center; "></th>
          <th style="text-align: center; "> 변경</th>
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
          if (list != null && list.size() != 0) {
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
          } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('검색 결과가 없습니다.')");
            script.println("</script>");
          }
        %>
        </tbody>
      </table>
    </form>
  </div>
</div>

</body>
</html>
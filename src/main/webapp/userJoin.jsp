<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <meta name="viewport" content="width=devoce-width" , initial-scale="1">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title>회원가입</title>
</head>

<body>
<div class="container">
    <div class="col-lg-4"></div>
    <div class="col-lg-4">
        <div class="jumbotron" style="padding-top: 20px;">
            <form method="post" action="userJoinAction.jsp">
                <h3 style="test-align: center;">회원가입</h3>
                <div class="form-group" style="test-align: center;">
                    <div class="btn-group" data-roggle="buttons">
                        <label class="btn btn-primary">
                            <input type="radio" id="admin" name="userType" autocomplete="off" onchange="setDisplay()" value="admin"> 관리자
                            <div id="adminCode" class="form-group" style="test-align: center;">
                                <input type="password" class="form-control" width="100" placeholder="관리자 등록 코드를 입력하세요"
                                       name="adminCode">
                            </div>
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" id="user" name="userType" autocomplete="off" value="user"> 사용자
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="아이디" name="userID" maxlength="20">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20">
                </div>
                <div class="form-group" style="test-align: center;">
                    <div class="btn-group" data-roggle="buttons">
                        <label class="btn btn-primary">
                            <input type="radio" name="userGender" autocomplete="off" value="남자"> 남자
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="userGender" autocomplete="off" value="여자"> 여자
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <input type="email" class="form-control" placeholder="이메일" name="userEmail" maxlength="100">
                </div>
                <input type="submit" class="btn btn-primary form-control" value="가입하기">
            </form>
        </div>
    </div>
    <div class="col-lg-4"></div>
</div>

<script src="https://code.jquery.com/jquery-3.1.1.min.js">
    $(document).ready(function () {
        $("input:radio[name=userType]").click(function () {
            if ($("input:radio[id=ad]:checked").val() == "admin") {
                $("#adminCode").style.display = "block";
            } else {
                $("#adminCode").style.display = "none";
            }
        });
    });
   function setDisplay(){
       if($('input:radio[id=userType]').is(':checked')){
            $("#adminCode").show();
       }
       else{
           $("#adminCode").hide();
       }
   }

</script>
<script src="js/bootstrap.js"></script>

</body>
</html>
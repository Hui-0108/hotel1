<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 | THE Ezo Hotels </title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">

<style type="text/css">
.lbl {
   position:absolute; 
   margin-left:15px; margin-top: 17px;
   color: #999999; font-size: 11pt;
}
.loginTF {
  width: 340px; height: 35px;
  padding: 5px;
  padding-left: 15px;
  border:1px solid #999999;
  color:#333333;
  margin-top:5px; margin-bottom:5px;
  font-size:14px;

}
</style>
<script type="text/javascript">
function bgLabel(ob, id) {
    if(!ob.value) {
	    document.getElementById(id).style.display="";
    } else {
	    document.getElementById(id).style.display="none";
    }
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
}

</script>


</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container">

	    <div style="margin: 80px auto 70px; width:600px;">
	    	<div style="text-align: left;" class="body-title">
	        	<span style=" font-size:27px; color: #424951; font-family: 'Oswald', sans-serif;">회원 로그인</span>
	        </div>
	        
	        <div style="text-align: center">
	        <p style="text-align: center;  font-size: 23px;  margin-bottom: 5px; color: #b77d30;">호텔 이조에 오신 것을 환영합니다.</p>
	        <p style="margin-bottom: 3px; color: #906935;">번호와 비밀번호를 입력해 주시기 바랍니다.</p>	
	        <p style="font-size: 12px; margin-bottom: 5px;">※ 호텔 이조의 회원이되시면 회원만을 위한 다양한 서비스와 혜택을 받으실 수 있습니다.</p>	
	        </div>
	        
	        
			<form name="loginForm" method="post" action="">
			  <table style="margin: 15px auto; width: 360px; border-spacing: 0px;">
			  <tr align="center" height="60"> 
			      <td> 
	                <label for="userId" id="lblUserId" class="lbl" >아이디</label>
			        <input type="text" name="userId" id="userId" class="loginTF" maxlength="15"
			                   tabindex="1"
	                           onfocus="document.getElementById('lblUserId').style.display='none';"
	                           onblur="bgLabel(this, 'lblUserId');">
			      </td>
			  </tr>
			  <tr align="center" height="60"> 
			      <td>
			        <label for="userPwd" id="lblUserPwd" class="lbl" >패스워드</label>
			        <input type="password" name="userPwd" id="userPwd" class="loginTF" maxlength="20" 
			                   tabindex="2"
	                           onfocus="document.getElementById('lblUserPwd').style.display='none';"
	                           onblur="bgLabel(this, 'lblUserPwd');">
			      </td>
			  </tr>
			  <tr align="center" height="65" > 
			      <td>
			        <button type="button" onclick="sendLogin();" class="btnConfirm">로그인</button>
			      </td>
			  </tr>


			  
			  <tr align="center" height="40" >
			    	<td><span style="color: #68461a;">${message}</span></td>
			  </tr>
			  
			  </table>
			</form>           
		</div>

</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>
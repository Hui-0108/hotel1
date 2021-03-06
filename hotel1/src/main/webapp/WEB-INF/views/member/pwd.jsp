<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
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

function sendOk() {
    var f = document.pwdForm;

    var str = f.userPwd.value;
    if(!str) {
        alert("\n패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }
    f.action = "${pageContext.request.contextPath}/member/pwd_ok.do";
    f.submit();
}

function deleteMember() {
    var f = document.pwdForm;
    
    var str = f.userPwd.value;
    if(!str) {
        alert("\n패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    if(confirm("탈퇴시 회원전용 할인혜택에서 제외 되실 수 있습니다. 탈퇴하시겠습니까?")){
        f.action = "${pageContext.request.contextPath}/member/pwd_ok.do";
        f.submit();
    }else{
       return;
    }
    	
	
	
}	


</script>


</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">

	    <div style="margin: 70px auto 60px; width:600px;">
		
	    	<div style="text-align: left;" class="body-title">
	        	<span style=" font-size:27px; color: #424951; font-family: 'Oswald', sans-serif;">비밀번호 재입력</span>
	        </div>
		
			<form name="pwdForm" method="post" action="">
			  <table style="width:420px; margin: 20px auto; padding:30px;  border-collapse: collapse; ">
			  <tr style="height:50px;"> 
			      <td style="padding-left: 30px; text-align: left; color: #906935; font-size: 15px;">
			          정보보호를 위해 비밀번호를 다시 한 번 입력해주세요.
			      </td>
			  </tr>

			  <tr style="height:60px;" align="center"> 
			      <td> 
			        &nbsp;
			        <input type="text" name="userId" class="loginTF" maxlength="15"
			                   tabindex="1"
			                   value="${sessionScope.member.userId}"
	                           readonly="readonly">
			           &nbsp;
			      </td>
			  </tr>
			  <tr align="center" height="60"> 
			      <td>
			        &nbsp;
			        
			        <input type="password" name="userPwd" id="userPwd" class="loginTF" maxlength="20" 
			                   tabindex="2"
	                           onfocus="document.getElementById('lblUserPwd').style.display='none';"
	                           onblur="bgLabel(this, 'lblUserPwd');">
			        &nbsp;
			      </td>
			  </tr>
			  <tr align="center" height="65" > 
			      <td>
			        &nbsp;
			        <c:choose>
			        <c:when test="${mode == 'update'}">
				        <button type="button" onclick="sendOk();" class="btnConfirm">확인</button>
						<input type="hidden" name="mode" value="${mode}">
			        </c:when>
					<c:otherwise>
				        <button type="button" onclick="deleteMember();" class="btnConfirm">탈퇴</button>
						<input type="hidden" name="mode" value="${mode}">						
					</c:otherwise>
			        </c:choose>
			        &nbsp;
			      </td>
			  </tr>
			  <tr align="center" height="10" > 
			      <td>&nbsp;</td>
			  </tr>
		    </table>
			</form>
		    <table style="width:420px; margin: 10px auto 0; border-collapse: collapse;">
			  <tr align="center" height="30" >
			    	<td><span style="color: brown;">${message}</span></td>
			  </tr>
			</table>
		</div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>


</body>
</html>
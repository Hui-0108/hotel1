<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약번호 확인 | THE Ezo Hotels </title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">

<style type="text/css">


.pharse1{
	text-align: center;
    font-size: 22px;
    padding-top: 20px;
    color: #b77d30;
}

.pharse2{
	text-align: center;
    font-size: 20px;
    padding-top: 5px;
	color: #906935;
}

.inputBox{
	margin-left: 15px;
    width: 390px;
    height: 40px;
    margin-top: 20px;
}
input.inputBox {
	text-align: center;
}

.reserveBtn{
    margin-left: 15px;
    width: 395px;
    height: 50px;
    margin-top: 20px;

}
.notice{
	text-align: center;
	font-size: 11px;
	padding-top: 10px;
}

.reserveBtn{
	font-size: 15px; 
	border:none;
	color:#ffffff;
	background:#3a3125;
	line-height: 50px;

}

select {
	text-align: center;
	margin-left: 15px;
    width: 390px;
    height: 40px;
    margin-top: 20px;
}

}
</style>
<script type="text/javascript">

function sendOk() {
	f = document.reserveNumForm;
	if(! f.num.value) {
		alert("예약번호를 입력하세요.");
		f.num.focus();
	}
	f.action = "${pageContext.request.contextPath}/"+f.mode.value+"/login_ok.do";
	f.submit();
}

</script>

</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="height: 450px;">

	<div style="margin: 70px auto 60px; width: 600x;">

	    <div style="text-align: left;" class="body-title">
	        <span style=" font-size:27px; color: #424951; font-family: 'Oswald', sans-serif;">예약번호 확인</span>
	    </div>
		<form name="reserveNumForm" method="post">
			<table style="width:420px; margin: 20px auto; padding:30px;  border-collapse: collapse;">
			

				<tr>
					<td class="pharse1">
						호텔 이조를 이용 해 주셔서 감사합니다.
					</td>
				</tr>			
				<tr>
					<td class="pharse2">
						고객님의 예약 번호를 입력해주세요.
					</td>
				</tr>
				
				<tr>
				<td class="notice">
				※ 문의사항은 고객게시판ㆍ고객센터(02-336-8546)를 이용해 주시기 바랍니다.
				</td> 				
				</tr>
				<tr>
					<td class="selectBox">
						<select name="mode" class="selectField">
							<option value="rr">룸</option>
							<option value="diningReserve">다이닝</option>
						</select>					
					</td>
				</tr>
				<tr>
					<td class="inputBox">
						<input type="text" name="num" class="inputBox" placeholder="예약번호">
					</td>
				</tr>
				<tr>
					<td >						
						<button type="button" onclick="sendOk();" class="reserveBtn">확인</button>
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
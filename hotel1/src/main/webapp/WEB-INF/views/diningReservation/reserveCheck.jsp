<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 예약 확인 | THE Ezo Hotels </title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/member.css" type="text/css">
<style type="text/css">

.tbName {
	text-align: center;
	padding-right: 15px;
}

.tbContent{
	text-align: center;
	padding-right: 20px;
	height: 40px;

}
.reserbtn{
	padding: 7px;
    border: none;
    color: #564242;
    margin-left: 5px;
    border-radius: 4px;
    background: #efeded;
}

.reserbtn:active, .reserbtn:focus, .reserbtn:hover {
	background-color:#e4e4e4;
	color: black;		
}

.diningTb{
	margin-top: 20px;
}
</style>
<script type="text/javascript">
function cancelDin() {
	
	
	
}
</script>

</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="min-height: 500px;">
	<div style="margin: 80px auto 70px; width:900px;">
    	<div style="text-align: left; margin-top: 30px;"  class="body-title">
        	<span style=" font-size:27px; color: #424951; font-family: 'Oswald', sans-serif;">다이닝 예약정보</span>
        </div>

		<table style="diningTb">
			<tr style="font-weight: 600">
				<td class="tbName" style="width: 150px">예약번호</td>
				<td class="tbName" style="width: 150px">레스토랑</td>
				<td class="tbName" style="width: 150px">좌석</td>
				<td class="tbName" style="width: 150px">예약인원</td>
				<td class="tbName" style="width: 150px">예약일</td>
				<td class="tbName" style="width: 150px"></td>
			</tr>
			<tr>
				<td class="tbContent">${dto.rodNum}</td>
				<td class="tbContent">${dto.dinName }</td>
				<td class="tbContent">${dto.seatType==1?'룸':'테이블'}</td>
				<td class="tbContent">${dto.guestCount}</td>
				<td class="tbContent">${dto.rsvDate} ${dto.rsvTime}</td>	
				<td align="center"> <button type="button" class="reserbtn" onclick="cancelDin(${dto.rodNum});">예약취소</button>   </td>
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
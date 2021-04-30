<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<meta charset="UTF-8">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<title>Insert title here</title>

<style type="text/css">
.tob-box {
	text-align: left;
	clear: both; 
	border-top: 3px solid #ccc; 
	border-bottom: 1px solid #ccc; 
	padding: 15px;
}

.tob-box span {
	display: inline-block;;
	vertical-align: middle;
}
.hotel-name {
	width: 120px;
	text-align:center;
	padding:3px;
	box-sizing: border-box;
	border: 1px solid brown;
	font-weight: 700;
	font-size: 14px;
	margin-right: 10px;
}

.body-box {
	padding: 5px;
	
}

</style>

<script type="text/javascript">
function reserveSubmit() {
	var f = document.reserveForm;
	
	if(! f.reserveNum.value) {
		return;
	}
	
	f.action="${pageContext.request.contextPath}/diningReserve/detail.do";
	f.submit();
}

function reserveChange() {
	var f = document.reserveForm;	
	if(! f.reserveNum.value) {
		return;
	}
	
	if(f.reserveNum.value == "${reserveNum}") {
		return;
	}
	
	f.action="${pageContext.request.contextPath}/diningReserve/reservation.do";
	f.submit();
}
function rsv() {
	var f = document.reserveForm;
	location.href="${pageContext.request.contextPath}/diningReserve/reserve.do?dinNum="+f.reserveNum.value;
}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="container">
	<div class="sidemenu">
			<h2>예약</h2>
		<ul>
			<li><a href="${pageContext.request.contextPath}/rr/list.do">룸 예약</a></li>
			<li><a>다이닝 예약</a></li>
		</ul>
	</div>
 <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 다이닝 예약</h3>
        </div>
        
        <div class="tob-box">
        	<form name="reserveForm" action="${pageContext.request.contextPath}/diningReserve/detail.do" method="post">
	        	<span class="hotel-name">
	        		HOTEL EZO
	        	</span>
        	
        		<span>
		        	<select name="reserveNum" onchange="reserveChange()" class="selectField">
		        		<option value="">다이닝 선택</option>
		        		<option value="1" ${reserveNum=="1"?"selected='selected'":"" }>라연</option>
		        		<option value="2" ${reserveNum=="2"?"selected='selected'":"" }>아리아께</option>
		        		<option value="3" ${reserveNum=="3"?"selected='selected'":"" }>더 파크뷰</option>
		        		<option value="4" ${reserveNum=="4"?"selected='selected'":"" }>팔선</option>
		       			<option value="5" ${reserveNum=="5"?"selected='selected'":"" }>콘티넨탈</option>
		        	</select>
				</span>
				<span>        	
	        		<button type="button" onclick="rsv();" class="btn"> 예약하기 </button>
	        	</span>
	        </form>
        </div>
        
        <div class="body-box">
        	<c:set var="url" value="/WEB-INF/views/diningReserve/re${reserveNum}.jsp"/>
        	<c:import var="info" url="${url}"/>
        	<c:out value="${info}" escapeXml="false"/>
        </div>
        
        
</div>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
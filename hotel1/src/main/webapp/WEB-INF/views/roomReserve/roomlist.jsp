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
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

 
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 객실 예약</h3>
        </div>
        
        <div class="tob-box">
        	<form name="reserveForm" action="${pageContext.request.contextPath}/diningReserve/detail.do" method="post">
	        	<span class="hotel-name">
	        		서울신라호텔
	        	</span>
	        	<div align="right">
        	<button type="button"  onclick="" class="btn"> 예약하기 </button>
	        </div>
	        
	        </form>
        </div>
        <span class="room-name">
	        		디럭스룸
	        	</span>
       
        <div align="center"  >
	 
        <div class="body-box">
        	<c:set var="url" value="/WEB-INF/views/roomReserve/de1.jsp"/>
        	<c:import var="info" url="${url}"/>
        	<c:out value="${info}" escapeXml="false"/>
        </div >
        
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
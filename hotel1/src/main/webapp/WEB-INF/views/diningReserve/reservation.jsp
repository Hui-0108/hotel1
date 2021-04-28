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

<script type="text/javascript">
function reserveSubmit() {
	var f = document.reserveForm;
	
	if(! f.reserveNum.value) {
		return;
	}
	
	f.submit();
}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

 <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 다이닝 예약</h3>
        </div>
        
        <div style="text-align: left; clear: both;">
        	<form name="reserveForm" action="${pageContext.request.contextPath}/diningReserve/detail.do" method="post">
	        	<span >
	        		서울신라호텔
	        	</span>
        	
        		<span>
		        	<select name="reserveNum">
		        		<option value="">다이닝 선택</option>
		        		<option value="1">라연</option>
		        		<option value="2">아리아께</option>
		        		<option value="3">더 파크뷰</option>
		        		<option value="4">팔선</option>
		       			<option value="5">콘티넨탈</option>
		        	</select>
				</span>
				<span>        	
	        		<button type="button" onclick="reserveSubmit()"> 예약하기 </button>
	        	</span>
	        </form>
        </div>
        
        
        
        
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div align="center">

	<a href="${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=1"><img src="${pageContext.request.contextPath}/resource/images/ra/lat.jpg" width="650" ></a>
	<hr>
	<br>
	<h2>세계 최초 미쉐린 3스타에 빛나는 한식당 라연</h2>
	<h6>전통의 맛을 현대적으로 재해석하여 차려낸 최고의 한식 정찬을 경험해 보시길 바랍니다.</h6>

	<h4>위치</h4> <h6>쌍용강북교육센터 2층</h6>
	<h4>운영시간</h4> <h6>lunch 12:00 ~ 14:30 dinner 17:30 ~ 21:30 </h6>
	<h4>문의</h4> <h6>Tel)02-112-119</h6>
	<br>
	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=1';">레스토랑 상세보기</button>
</div>
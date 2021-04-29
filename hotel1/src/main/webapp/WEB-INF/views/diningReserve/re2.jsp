<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div align="center">
<a href="${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=2"><img src="${pageContext.request.contextPath}/resource/images/ari/ari2.jpg" width="650" ></a>
		<hr>
	<br>
	<h2>오감 만족의 정통 일식당</h2>
	<h6>전통과 현대적인 감각이 공존하는 공간에서 즐기는 일식 정통 요리의 진수</h6>

	<h4>위치</h4> <h6>쌍용강북교육센터 2층 사무실</h6>
	<h4>운영시간</h4> <h6>lunch 12:00 ~ 14:30 dinner 17:30 ~ 21:30 </h6>
	<h4>문의</h4> <h6>Tel)02-112-119</h6>
	<br>
	<h5>※ 스시카운터 예약 및 특별 프로모션 기간 예약은 유선상으로만 가능합니다.</h5>
	<br>
	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=2';">레스토랑 상세보기</button>
</div>
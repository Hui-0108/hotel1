<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div align="center">

	<a href="${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=5"><img src="${pageContext.request.contextPath}/resource/images/cont/con1.jpg" width="650" ></a>
	<hr>
	<br>
	<h2>컨템포러리 프렌치 정찬 레스토랑</h2>
	<h6>화려한 플레이팅과 독창적인 맛의 프렌치 코스요리 및 다채로운 와인 셀렉션을 경험해 보시길 바랍니다.</h6>

	<h4>위치</h4> <h6>쌍용강북교육센터 128층</h6>
	<h4>운영시간</h4> <h6>lunch 12:00 ~ 14:30 dinner 17:30 ~ 21:30 </h6>
	<h4>문의</h4> <h6>Tel)02-1541</h6>
	<br>
	<h5>※ 2월 14일, 12월 24~25일, 및 31일 등 특정일 예약은 전화 문의 바랍니다.</h5>
	<br>
	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=5';">레스토랑 상세보기</button>
</div>
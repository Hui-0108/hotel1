<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div align="center">

	<a href="${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=4"><img src="${pageContext.request.contextPath}/resource/images/pal/pal1.jpg" width="650" ></a>
	<hr>
	<br>
	<h2>중식 명가의 품격</h2>
	<h6>팔선의 깊은 전통과 꾸준한 명성으로 중국 본토 최고의 맛을 그대로 전해드립니다.</h6>

	<h4>위치</h4> <h6>쌍용강북교육센터 지하1층</h6>
	<h4>운영시간</h4> <h6>lunch 12:00 ~ 14:30 dinner 17:30 ~ 21:30 </h6>
	<h4>문의</h4> <h6>Tel)02-1541</h6>
	<br>
	<h5>※ 해외 초청 이벤트는 전화 문의 바랍니다.</h5>
	<br>
	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=4';">레스토랑 상세보기</button>
</div>
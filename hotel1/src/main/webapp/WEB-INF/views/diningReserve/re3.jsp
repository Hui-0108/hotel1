<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div align="center">

	<a href="${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=3"><img src="${pageContext.request.contextPath}/resource/images/pk/pk1.jpg" width="650" ></a>
	<hr>
	<br>
	<h2>자연을 닮은 올 데이 다이닝 레스토랑</h2>
	<h6>라이브 오픈 키친에서 갓 조리된 신선한 요리를 제공하며,전 세계 미식 트렌드를 한번에 만나는 뷔페</h6>

	<h4>위치</h4> <h6>쌍용강북교육센터 63층</h6>
	<h4>운영시간</h4> <h6>lunch 12:00 ~ 14:30 dinner 17:30 ~ 21:30 </h6>
	<h4>문의</h4> <h6>Tel)1577-1577</h6>
	<br>
	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/diningReserve/detail.do?reserveNum=3';">레스토랑 상세보기</button>
</div>
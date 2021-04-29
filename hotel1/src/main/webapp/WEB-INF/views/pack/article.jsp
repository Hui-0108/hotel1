<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hotel Ezo</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,700&display=swap" rel="stylesheet">
<style type="text/css">
#zemok{
	font-size: 50px; 
	font-family: 'Playfair Display', serif;
	padding-left: 15px;
}
</style>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deletePack(pkgNum) {
    var query = "pkgNum="+pkgNum+"&page=${page}";
    var url = "${pageContext.request.contextPath}/pack/delete.do?" + query;

    if(confirm("위 자료를 삭제 하시 겠습니까 ? ")) {
    	location.href=url;
    }
}
</c:if>
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
	<div class="sidemenu">
			<h2>프로모션</h2>
		<ul>
			<li><a href="${pageContext.request.contextPath}/pack/list.do}">객실패키지</a></li>
			<li><a href="${pageContext.request.contextPath}/event/list.do}">이벤트</a></li>
		</ul>
	</div>
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 객실패키지 </h3>
        </div>
        
        <div>
            <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
				<tr height="70">
				    <td id="zemok" colspan="2" align="left">
						${dto.pkgName}
				    </td>
				</tr>
						
				<tr>
					<td colspan="2" align="left" style="padding: 10px 5px 10px;">
						호텔 이조 회원에게만 제공하는 특별한 가격<br><br>
						기간 : ${dto.startDate} ~ ${dto.endDate}<br><br>
				    	${dto.content}
					</td>
				</tr>
			</table>
			
			<table border="1" style="border: 1px solid #cdcbbe; border-right: none; border-left: none; border-collapse: collapse; width: 100%">
				<c:if test="${dto.deluxe!=null && dto.deluxe!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">디럭스 룸</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.deluxe}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<c:if test="${dto.bDeluxe!=null && dto.bDeluxe!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">비즈니스 디럭스 룸</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.bDeluxe}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<c:if test="${dto.gcDeluxe!=null && dto.gcDeluxe!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">그랜드 코너 디럭스 룸</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.gcDeluxe}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<c:if test="${dto.ebDeluxe!=null && dto.ebDeluxe!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">이그제큐티브<br>비즈니스 디럭스 룸</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.ebDeluxe}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 더 이그제큐티브 라운지 혜택(2인)</li>
								<li>· 실내 사우나 혜택(2인)</li>
								<li>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<c:if test="${dto.egDeluxe!=null && dto.egDeluxe!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">이그제큐티브<br>그랜드 디럭스 룸</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.egDeluxe}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 더 이그제큐티브 라운지 혜택(2인)</li>
								<li>· 실내 사우나 혜택(2인)</li>
								<li>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<c:if test="${dto.sSuite!=null && dto.sSuite!=''}">
					<tr>
						<td style="background: #faf9f4; width: 200px; height: 120px; color: #856f56; font-weight: bold; text-align: center;">수페리어 스위트</td>
						<td style="padding-left: 50px;">
							<b style="font-size: 14px;">${dto.sSuite}원 ~</b>
							<ul style="font-size: 13px; list-style: none;">
								<li><br>· 더 이그제큐티브 라운지 혜택(2인)</li>
								<li>· 실내 사우나 혜택(2인)</li>
								<li>· 체련장(Gym), 실내 수영장 혜택(2인)</li>
							</ul>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2" align="left" style="padding: 10px 5px;">
				   		<img src="${pageContext.request.contextPath}/uploads/pack/${dto.imageFilename}" style="width:100%; height:auto; resize:both;">
				   	</td>
				</tr>
			</table>
			
			<table style="width: 100%; margin: 0 auto 20px; border-spacing: 0px;">
				<tr height="45">
				    <td width="300" align="left">
				    	<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/pack/update.do?pkgNum=${dto.pkgNum}&page=${page}';">수정</button>
				    		</c:when>
				    	</c:choose>
				    	<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="deletePack('${dto.pkgNum}');">삭제</button>
				    		</c:when>
				    	</c:choose>
				    </td>
				
				    <td align="right">
				        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/pack/list.do?page=${page}';">리스트</button>
				        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/#';">예약하기</button>
					</td>
				</tr>
			</table>
        </div>
        
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
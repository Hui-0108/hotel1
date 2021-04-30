<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/rsv/result.css" type="text/css">
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 예약 결과 </h3>
        </div>
        
        <c:if test="${dto!=null}">
        <div class="layout">
            <table>
            	<tr class="tableTitle">
            		<td colspan="2">다이닝 예약 정보</td>
            		<td></td>
            		<td></td>
            	</tr>
            	<tr>
            		<td>예약번호</td>
            		<td>${dto.rodNum}</td>
            		<td></td>
            		<td></td>
            	</tr>
            	<tr>
            		<td>다이닝 이름</td>
            		<td>${dto.dinName}</td>
            		<td>좌석 유형</td>
            		<td>${dto.seatType==1?'룸':'테이블'}</td>
            	</tr>
            	<tr>
            		<td>날짜</td>
            		<td>${dto.rsvDate} ${dto.rsvTime}</td>
            		<td>예약 인원</td>
            		<td>${dto.guestCount}</td>
            	</tr>
            </table>
            
            
            <div class="msg">
            	<span>저희 호텔을 이용해 주셔서 감사합니다.</span>
            </div>
        </div>
        </c:if>
        <c:if test="${dto==null}">
        	<h3>예약이 실패하였습니다.</h3>
        </c:if>
        
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
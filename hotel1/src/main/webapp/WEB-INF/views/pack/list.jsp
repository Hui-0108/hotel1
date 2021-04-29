<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hotel Ezo</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

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
            <c:forEach var="dto" items="${list}">
	        		<table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
		        		<tr>
		        			<td rowspan="3" width="320px"><img src="${pageContext.request.contextPath}/uploads/pack/${dto.thumbnail}" width="270" height="150"></td>
		        			<td style="padding-top: 20px;"><a href="${articleUrl}&pkgNum=${dto.pkgNum}" style="font-size: 17px; color: #a1886f;">${dto.pkgName}</a></td>
		        		</tr>
		        		<tr>
		        			<td style="font-size: 12px; padding-bottom: 10px; border-bottom: 1px dotted #aaa;">${dto.startDate}&nbsp;~&nbsp;${dto.endDate}</td>
		        		</tr>
		        		<tr>
		        			<td style="font-size: 12px; padding-top: 10px;">${dto.summary}<br><br>
								<c:choose>
									<c:when test="${dto.deluxe!=null && dto.deluxe!=''}">
										${dto.deluxe}원 ~
									</c:when>
									<c:when test="${dto.bDeluxe!=null && dto.bDeluxe!=''}">
										${dto.bDeluxe}원 ~
									</c:when>
									<c:when test="${dto.gcDeluxe!=null && dto.gcDeluxe!=''}">
										${dto.gcDeluxe}원 ~
									</c:when>
									<c:when test="${dto.ebDeluxe!=null && dto.ebDeluxe!=''}">
										${dto.ebDeluxe}원 ~
									</c:when>
									<c:when test="${dto.egDeluxe!=null && dto.egDeluxe!=''}">
										${dto.egDeluxe}원 ~
									</c:when>
									<c:when test="${dto.sSuite!=null && dto.sSuite!=''}">
										${dto.sSuite}원 ~
									</c:when>
								</c:choose>
							</td>
		        		</tr>
	        		</table>
        		</c:forEach>
	        <table style="width:100%; border-spacing:0px;">
		        <tr height="50">
			        <td align="center">
			      		${dataCount==0?"등록된 게시물이 없습니다.":paging}
			        </td>
		        </tr>
	        </table>
	        <table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   	<tr height="40">
			     	<td align="left" width="50%">
			        	&nbsp;
			      	</td>
			      	<td align="right" width="50%">
			      		<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
			        			<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/pack/created.do';">패키지작성</button>
			        		</c:when>
			        	</c:choose>
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
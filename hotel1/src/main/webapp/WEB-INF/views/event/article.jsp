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
<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deleteEvent(eventNum) {
    var query = "eventNum="+eventNum+"&page=${page}";
    var url = "${pageContext.request.contextPath}/event/delete.do?" + query;

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
            <h3><i class="fas fa-chalkboard"></i> 이벤트 </h3>
        </div>
        
        <div>
        	<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
				<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
				    <td colspan="2" align="center">
						${dto.eventName}
				    </td>
				</tr>
			
				<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
				    <td colspan="2" align="right">
						기간 : ${dto.startDate} ~ ${dto.endDate}
				    </td>
				</tr>
	
				<tr>
					<td colspan="2" align="left" style="padding: 10px 5px;">
				   		<img src="${pageContext.request.contextPath}/uploads/event/${dto.imageFilename}" style="max-width:100%; height:auto; resize:both;">
				   	</td>
				</tr>
						
				<tr style="border-bottom: 1px solid #cccccc;">
					<td colspan="2" align="left" style="padding: 10px 5px 10px;">
				    	${dto.content}
					</td>
				</tr>
			</table>
			
			<table style="width: 100%; margin: 0 auto 20px; border-spacing: 0px;">
				<tr height="45">
				    <td width="300" align="left">
				    	<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/update.do?eventNum=${dto.eventNum}&page=${page}';">수정</button>
				    		</c:when>
				    	</c:choose>
				    	<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="deleteEvent('${dto.eventNum}');">삭제</button>
				    		</c:when>
				    	</c:choose>
				    </td>
				
				    <td align="right">
				        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/list.do?page=${page}';">리스트</button>
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
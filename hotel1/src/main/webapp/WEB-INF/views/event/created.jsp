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
            <h3><i class="fas fa-chalkboard"></i> 이벤트 </h3>
        </div>
        
        <div>
	        <form name="eventForm" method="post" enctype="multipart/form-data">
				<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
					<tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
						<td width="100" bgcolor="#fff3e0" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						<td style="padding-left:10px;"> 
						<input type="text" name="subject" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.eventName}">
						</td>
					</tr>
						
					<tr align="left" style="border-bottom: 1px solid #cccccc;"> 
						<td width="100" bgcolor="#fff3e0" style="text-align: center; padding-top:5px;" valign="top">설&nbsp;&nbsp;&nbsp;&nbsp;명</td>
						<td valign="top" style="padding:5px 0px 5px 10px;"> 
						<textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
						</td>
					</tr>
								  
					<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
						<td width="100" bgcolor="#fff3e0" style="text-align: center;">이미지</td>
						<td style="padding-left:10px;"> 
						<input type="file" name="selectFile" accept="image/*"
						class="boxTF" size="53" style="height: 25px;">
						</td>
					</tr>
					
					<c:if test="${mode=='update'}">
						<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
							<td width="100" bgcolor="#fff3e0" style="text-align: center;">등록이미지</td>
							<td style="padding-left:10px;"> 
							<img src="${pageContext.request.contextPath}/uploads/event/${dto.imageFilename}"
							width="30" height="30" border="0" style="vertical-align: middle;" >
							<span style="vertical-align: middle; font-size: 11px; color: #333;">(새로운 이미지가 등록되면 기존 이미지는 삭제 됩니다.)</span>
							</td>
						</tr> 
					</c:if>			  
				</table>
							  			
				<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
					<tr height="45"> 
						<td align="center" >
							<button type="button" class="btn" onclick="">${mode=='update'?'수정완료':'등록하기'}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
							<c:if test="${mode=='update'}">
								<input type="hidden" name="num" value="${dto.num}">
								<input type="hidden" name="imageFilename" value="${dto.imageFilename}">
						        <input type="hidden" name="page" value="${page}">
							</c:if>
						</td>
					</tr>
				</table>
			</form>
        </div>
        
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
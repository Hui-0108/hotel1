<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA_created</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<script type="text/javascript">
	function sendOk() {
 		var f = document.qnaForm;
	
		str = f.nickname.value;
  		if(!str) {
        	alert("QnA를 등록하기 위해서는 작성자 닉네임이 필요합니다.");
        	f.nickname.focus();
        	return;
    	}
  		str = f.subject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.subject.focus();
            return;
        }

    	str = f.content.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.content.focus();
            return;
        }

    	f.action="${pageContext.request.contextPath}/board/${mode}_ok.do";

        f.submit();
    }
</script>

</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div class="container">
		<div class="body-container" style="width: 700px;">
			<div class="body-title" style="border-bottom: 1px solid #f1e3c4;">
				<h3 style="border-bottom: 3px solid #f1e3c4;">QnA</h3>
			</div>
			
			<div>
				<form name="qnaForm" method="post">
					<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
						<tr align="left" height="40" style="border-top: 1px solid #f1e3c4; ">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">*&nbsp; 질문유형</td>
							<td style="padding-left: 10px;">
								<select name="ctg" class="selectField">
									<option value="customer">회원/정보 관리</option>
									<option value="order">주문/결제</option>
									<option value="reservation"	>예약 변경/취소</option>
									<option value="service">회원서비스</option>
									<option value = "room">객실</option>
									<option value="etc">기타</option>
								</select>
							</td>							
						</tr>
						
						
					<c:choose>
					<c:when test="${mode=='reply'}">
						<tr align="left" height="40">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">*&nbsp; 작성자</td>
							<td style="padding-left: 10px;"><input type="text" name="nickname" class="boxTF" style="width:40%;"
								value="관리자"></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr align="left" height="40">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">*&nbsp; 작성자</td>
							<td style="padding-left: 10px;"><input type="text" name="nickname" class="boxTF" style="width:40%;"
								value="${dto.nickname}"></td>
						</tr>
					</c:otherwise>
					</c:choose>	
					
					
						<tr align="left" height="40">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">*&nbsp; 제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
							<td style="padding-left: 10px;"><input type="text"
								name="subject" maxlength="100" class="boxTF" style="width: 95%;"
								value="${dto.subject}"></td>
						</tr>
						<tr align="left">
							<td width="100" bgcolor="#f1e3c4"
								style="text-align: center; padding-top: 5px;" valign="top">*&nbsp; 문의내용</td>
							<td valign="top" style="padding: 5px 0px 5px 10px;"><textarea
									name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
							</td>
						</tr>
					
					<c:choose>
					<c:when test="${mode=='reply'}">
					
					</c:when>
					<c:otherwise>
						<tr align="left" height="40">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">*&nbsp; 비밀번호</td>
							<td style="padding-left: 10px;"><input type="password" name="qPwd" class="boxTF" style="width:40;">${dto.qPwd}</td>
						</tr>
						<tr align="left" height="40" style="border-bottom: 1px solid #f1e3c4;">
							<td width="100" bgcolor="#f1e3c4" style="text-align: center;">&nbsp;이메일</td>
							<td style="padding-left: 10px;"><input type="text"
								name="subject" class="boxTF" style="width: 95%;"
								value="${dto.email}"></td>
						</tr>
					</c:otherwise>				
					</c:choose>	
					
					</table>
					
					<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
						<tr height="45">
							<td align="center">
								<c:if test="${mode=='update'}">
									<input type="hidden" name="qNum" value="${dto.qNum}">
									<input type="hidden" name="page" value="${page}">
									<input type="hidden" name="condition" value="${condition}">
									<input type="hidden" name="keyword" value="${keyword}">
								</c:if> <c:if test="${mode=='reply'}">
									<input type="hidden" name="groupNum" value="${dto.groupNum}">
									<input type="hidden" name="orderNo" value="${dto.orderNo}">
									<input type="hidden" name="depth" value="${dto.depth}">
									<input type="hidden" name="parent" value="${dto.qNum}">
									<input type="hidden" name="page" value="${page}">
									
								</c:if>
								<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':(mode=='reply'? '답변완료':'등록하기')}</button>
								<button type="reset" class="btn">다시입력</button>
								<button type="button" class="btn"
									onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do';">${mode=='update'?'수정취소':(mode=='reply'? '답변취소':'등록취소')}</button>
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
	
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />


</body>
</html>
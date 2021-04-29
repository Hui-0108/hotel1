<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA_list</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<script type="text/javascript">
	function searchList() {
		var f = document.searchForm;
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
				<table
					style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
					<tr height="35">
						<td align="left" width="50%">
							${dataCount}개(${page}/${total_page} 페이지)</td>
						<td align="right">&nbsp;</td>
					</tr>
				</table>

				<table
					style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
					<tr align="center" bgcolor="#f1e3c4" height="35"
						style="border-top: 1px solid #f1e3c4; border-bottom: 1px solid #f1e3c4;">
						<th width="60" style="color: #787878;">번호</th>
						<th style="color: #787878;">제목</th>
						<th width="100" style="color: #787878;">작성자</th>
						<th width="80" style="color: #787878;">작성일</th>
					</tr>

					<c:forEach var="dto" items="${list}">
						<tr align="center" bgcolor="#ffffff" height="35"
							style="border-bottom: 1px solid #f1e3c4;">
							<td>${dto.listNum}</td>
							<td align="left" style="padding-left: 10px;"><c:forEach
									var="n" begin="1" end="${dto.depth }">&nbsp;&nbsp;
								</c:forEach> <c:if test="${dto.depth!=0}">└&nbsp;</c:if> <a
								href="${articleUrl}&qNum=${dto.qNum}">${dto.subject}</a></td>
							<td>${dto.nickname}</td>
							<td>${dto.created}</td>

						</tr>
					</c:forEach>

				</table>

				<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
					<tr height="35">
						<td align="center"><br>${dataCount==0?"등록된 게시물이 없습니다.":paging}<br></td>
					</tr>
				</table>

				<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
					<tr height="40">
						<td align="left" width="100">
							<button type="button" class="btn"
								onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do';">새로고침</button>
						</td>
						<td align="center">
							<form name="searchForm"
								action="${pageContext.request.contextPath}/qna/list.do"
								method="post">
								<select name="condition" class="selectField">
									<option value="subject"
										${condition=="subject"?"selected='selected'":"" }>제목</option>
									<option value="nickname"
										${condition=="nickname"?"selected='selected'":"" }>작성자</option>
								</select> <input type="text" name="keyword" class="boxTF"
									value="${keyword}">
								<button type="button" class="btn" onclick="searchList()">검색</button>
							</form>
						</td>
						<td align="right" width="100">
							<button type="button" class="btn"
								onclick="javascript:location.href='${pageContext.request.contextPath}/qna/created.do';">글올리기</button>
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
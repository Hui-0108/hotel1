<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA_article</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<script type="text/javascript">
	function deleteQnA(qNum) {
		if (confirm("게시물을 삭제 하시겠습니까 ?")) {
			var url = "${pageContext.request.contextPath}/qna/delete.do?qNum="
					+ qNum + "&${query}";
			location.href = url;
		}
	}
</script>

</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<div class="container">
		<div class="sidemenu">
			<h2>고객문의</h2>
			<ul>
				<li><a>F&Q</a></li>
				<li><a>Q&A</a></li>
			</ul>
		</div>
		<div class="body-container" style="width: 700px;">
			<div class="body-title"
				style="border-bottom: 1px solid #f1e3c4; margin-bottom: 0px;">
				<h3 style="border-bottom: 3px solid #f1e3c4;">QnA</h3>
			</div>

			<div>
				<table
					style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse; margin: 5px 0px;">


					<tr height="35"
						style="border-bottom: 1px solid #f1e3c4; margin: 5px 0px;">
						<td colspan="2" align="center"><c:if test="${dto.depth!=0 }">[Re] </c:if>
							${dto.subject}</td>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #f1e3c4;">
						<td width="50%" align="left" style="padding-left: 5px;">작성자 :
							${dto.nickname}</td>
						<td width="50%" align="right" style="padding-right: 5px;">
							${dto.created}</td>
					</tr>

					<tr style="border-bottom: 1px solid #f1e3c4;">
						<td colspan="2" align="left" style="padding: 10px 5px;"
							valign="top" height="200">${dto.content}</td>
					</tr>


				</table>

				<table
					style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
					<tr height="45">
						<td width="300" align="left">
							<!-- <button type="button" class="btn"
								onclick="javascript:location.href='${pageContext.request.contextPath}/qna/reply.do?qNum=${dto.qNum}&page=${page}';">답변</button>
							<button type="button" class="btn"
								onclick="javascript:location.href='${pageContext.request.contextPath}/qna/update.do?qNum=${dto.qNum}&${query}';">수정</button>
							
							-->
								
							<button type="button" class="btn"
								onclick="deleteQnA('${dto.qNum}');">삭제</button>

						</td>

						<td align="right">
							<button type="button" class="btn"
								onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do?${query}';">리스트</button>
						</td>
					</tr>
				</table>
			</div>

		</div>
	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>
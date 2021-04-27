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

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
	<div class="sidemenu">
			<h2>제목</h2>
		<ul>
			<li><a>소제목</a></li>
			<li><a>소제목</a></li>
		</ul>
	</div>
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3> 날짜, 인원선택 </h3>
        </div>
        
        <div class="body-content">
            <form method="post" name="conditionForm">
            	<table>
            		<tr>
            			<td> <span>체크인</span> </td>
            			<td> <img src="${pageContext.request.contextPath}/resource/images/rsv/ico_night.png"> </td>
            			<td> <span>체크아웃</span> </td>
            			<td> <span>숙박인원</span> </td>
            			<td colspan="2"> <button type="button" onclick="searchList">검색</button> </td>
            		</tr>
            		<tr>
            			<td> <input type="date" min="2021-04-27"> </td>
            			<td> 1박 </td>
            			<td> <input type="date" min="2021-04-28"> </td>
            			<td> <input type="number" maxlength="1" min="1" max="4"> </td>
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
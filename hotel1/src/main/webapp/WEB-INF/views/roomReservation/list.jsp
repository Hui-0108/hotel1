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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/rsv/list.css" type="text/css">
<script type="text/javascript">
function countUp() {
	var f = document.getElementById("guestCount");
	if(f.value > 3) {
		return;
	}
	f.value++;
}
function countDown() {
	var f = document.getElementById("guestCount");
	if(f.value < 2) {
		return;
	}
	f.value--;
}
function calcNight() {
	var f = document.getElementById("nights");
	var cIn = new Date(document.getElementById("checkIn").value);
	var cOut = new Date(document.getElementById("checkOut").value);
	
	if(document.getElementById("checkIn").value=="" || document.getElementById("checkOut").value=="") {
		f.innerHTML = 0;
		return;
	}
	
	f.innerHTML = (cOut.getTime()-cIn.getTime()) / (1000*60*60*24);
}
function searchList() {
	var f = document.conditionForm;
	f.action = "${pageContext.request.contextPath}/rr/list.do";
	f.submit();
}

window.onload = function() {
	calcNight();
}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
	<div class="sidemenu">
			<h2>예약</h2>
		<ul>
			<li><a>룸 예약</a></li>
			<li><a>다이닝 예약</a></li>
		</ul>
	</div>
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3> 날짜, 인원선택 </h3>
        </div>
        
        <div class="body-search">
            <form method="post" name="conditionForm">
            	<table>
            		<tr>
            			<td align="center"> <span>체크인</span> </td>
            			<td align="center"> <img src="${pageContext.request.contextPath}/resource/images/rsv/ico_night.png"> </td>
            			<td align="center"> <span>체크아웃</span> </td>
            			<td align="center"> <span>숙박인원</span> </td>
            			<td rowspan="2" align="center"> <button id="searchBtn" class="btnConfirm" type="button" onclick="searchList();">검색</button> </td>
            		</tr>
            		<tr>
            			<td align="center"> <input name="checkIn"  class="boxTF"  id="checkIn" type="date" onchange="calcNight()" value="${checkIn}"> </td>
            			<td align="center"> <span id="nights">${nights}</span>박 </td>
            			<td align="center"> <input name="checkOut"  class="boxTF"  id="checkOut" type="date" onchange="calcNight()" value="${checkOut}"> </td>
            			<td align="center"> 
            			<button type="button" name="downBtn" class="btn" onclick="countDown();">↓</button>
            			<input name="guestCount"  class="boxTF"  id="guestCount" type="text" min="1" max="4" value="${guestCount}" style="text-align: center;" readonly="readonly" > 
            			<button type="button" name="upBtn" class="btn" onclick="countUp();">↑</button>
            			</td>
            		</tr>
            	</table>
            	<div class="msg">
            		<span ></span>
            	</div>
            </form>
        </div>
        <hr>
        
        <div class="body-list">
        	<table>
	        	<c:forEach var="dto" items="${list}">
				  <tr align="center" height="35" style="border-bottom: 1px solid #cccccc;"> 
				      <td>${dto.num}</td>
				      <td align="left" style="padding-left: 10px;">
				           <a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
				      </td>
				      <td>${dto.userName}</td>
				      <td>${dto.created}</td>
				      <td>${dto.hitCount}</td>
				  </tr>
				</c:forEach>
			</table>
        </div>
        
        <p>
        	<a href="${pageContext.request.contextPath}/rr/reserve.do?classNum=1&checkIn='2021-05-01'&checkOut='2021-05-02">로그인상태 예약</a>
        	<a href="${pageContext.request.contextPath}/rr/reserve.do?classNum=1&checkIn='2021-05-01'&checkOut='2021-05-02">비회원상태 예약</a>
        </p>
        
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
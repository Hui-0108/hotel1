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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/slider/css/jquery.bxslider.min.css">
<style type="text/css">
.imgLayout {
	width: 600px;
	margin: 10px auto;
}

.slider img {
	width: 600px;
	height: 350px;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/slider/js/jquery.bxslider.min.js"></script>

<script type="text/javascript">
$(function(){
	$('.slider').bxSlider({
		auto: true,				// 자동 애니메이션 시작
		speed: 500,				// 애니메이션 속도
		pause: 5000,			// 애니메이션 유지시간(단위:ms) 
		mode: 'horizontal',		// 기본값. 슬라이드 모드 : 'fade', 'horizontal', 'vertical'
		autoControls: true,		// 시작 및 중지 버튼
		pager: true,			// 동그라미(불릿) 버튼 노출 여부
		captions: true,			// 이미지 위에 텍스트 표시
		touchEnabled: false		// <a href="주소"> 에서 설정한 주소로 이동 가능하도록
	});
});
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	

							
					
				
				
    <div class="body-container" style="width: 800px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i>이그제큐티브 디럭스룸 </h3>
        </div>
        
        <div>
			
        	<div class="imgLayout">
				<ul class="slider" style="margin: 0;">
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/exgrde/exgr1.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/exgrde/exgr2.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/exgrde/exgr3.jpg"></a></li>
			
				</ul>
			    <img src="${pageContext.request.contextPath}/resource/images/exgrde/exgrinfo.gif"  width="400">
	
           </div>
           			
        </div>
        </div>
        <div class="diningtext"></div>
    <div class="HotelBoxTop"></div> 
<div class="conTitle2" align="center"> 
<h3 class="tit">Hotel Info.</h3></div> 
<div align="center">
<h5>조식 이용 안내</h5>
<h6>
- 더 이그제큐티브 라운지 06:30~10:00</h6>
<h5>체크인/체크아웃 시간</h5>
<h6>
- 체크인 : 오후 3시 이후

- 체크아웃 : 낮 12시</h6>
<h5>취소/변경 및 노쇼(No-show) 안내</h5>
<h6>- 숙박 예정일 1일 전 18시까지는 위약금 없음

- 숙박 예정일 1일 전 18시 이후 취소/변경/노쇼 발생 시
<br>
성수기(5월~10월, 12월 24일~31일) :
최초 1일 숙박 요금의 80%가 위약금으로 부과
비수기(성수기 외 기간) :
최초 1일 숙박 요금의 10%가 위약금으로 부과</h6>
</div>
<br>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
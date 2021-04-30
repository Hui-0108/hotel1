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
	

							
					
				
				
    <div class="body-container" style="width: 100%; margin: auto;">
        <div class="body-title" style="width: 700px; margin: auto;">
            <h3><i class="fas fa-chalkboard"></i>코너 스위트룸 </h3>
        </div>
        
        <div style="padding-top: 30px;">
			
        	<div class="imgLayout">
				<ul class="slider" style="margin: 0;">
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/cosu/cs1.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/cosu/cs2.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/cosu/cs3.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/cosu/cs4.jpg"></a></li>
			
				</ul>
			    <img src="${pageContext.request.contextPath}/resource/images/cosu/csinfo.gif"  width="400">
	
           </div>
           			
        </div>
              <div class="diningtext">
    <div class="HotelBoxTop">
<div class="conTitle2" align="center"  style="padding-top: 30px;"> 
<h3 class="tit">Hotel Info.</h3></div> 

<div align="center">
<h5>조식 이용 안내</h5>
<h6>

- 더 파크뷰 06:00~10:00(주중/주말/공휴일)

- 더 라이브러리 08:00~11:30
<br>

※ 현재 더 라이브러리는 코로나19로 인한 운영시간
축소로 조식 운영을 하지 않고 있사오니 이용에
참고 부탁드립니다.</h6>


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
</div> 
</div>
<br>
        </div>
        
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
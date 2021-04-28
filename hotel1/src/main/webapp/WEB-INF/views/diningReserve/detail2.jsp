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
		pager: false,			// 동그라미(불릿) 버튼 노출 여부
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
	
<div class="container">
	<div class="sidemenu">
			<h2>아리아께</h2>
		<div class="rightArea" id="rightArea">
	<div class="dnBox">
	<dl><dt><img alt="Location" src="${pageContext.request.contextPath}/resource/images/we.gif"> </dt><dd>
	  <span class="loc">쌍용강북교육센터 2층 사무실</span>
	<dl class="Open"><dt><img alt="Open / Close" src="${pageContext.request.contextPath}/resource/images/luk.gif"> </dt><dd> 
	<p class="first"><img alt="Lunch :" src="${pageContext.request.contextPath}/resource/images/lunch.gif">
 	<span class="lunchTime">12:00 ~ 14:30</span></p> 
	<p><img alt="Dinner :" src="${pageContext.request.contextPath}/resource/images/dinner.gif">
	 <span class="dinnerTime">17:30 ~ 21:30</span></p></dd></dl> 
	 <span class="dinnerTime">*스시카운터 Last order는 20:00분까지 가능합니다.</span></dd></dl> 
	 <dl><dt><img alt="예약 및 문의" src="${pageContext.request.contextPath}/resource/images/yeah.gif"> </dt><dd>
	  <span class="Tel">Tel)02-112-119</span>
	 <dl class="mgB0"><dt><img alt="좌석수" src="${pageContext.request.contextPath}/resource/images/zua.gif"> </dt><dd>
	  <span class="Tel">좌석93석 룸5실</span>  </dd></dl></dd></dl>
	  
	  </div>
							
					
				
				
				</div>
					</div>
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><i class="fas fa-chalkboard"></i> 레스토랑 상세 페이지 </h3>
        </div>
        
        <div>
			
        	<div class="imgLayout">
				<ul class="slider" style="margin: 0;">
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari2.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari3.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari4.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari5.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari6.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari7.jpg"></a></li>
					<li><a href="#"><img src="${pageContext.request.contextPath}/resource/images/ari/ari8.jpg"></a></li>
				</ul>
				<img src="${pageContext.request.contextPath}/resource/images/ari/ariinfo1.gif"  width="400">
			    <img src="${pageContext.request.contextPath}/resource/images/ari/ari1.jpg"  width="400">
				<img src="${pageContext.request.contextPath}/resource/images/ari/ariinfo2.gif"  width="400">
           </div>
           			
        </div>
        <div class="diningtext">
    			
        
        </div>
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
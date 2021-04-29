<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The HOTEL EZO</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">

.mainImg{
	width: 960px;
	height: 500px;	
}

.mainImg > img{
    position: absolute;
    left : 0;
    width: 100%;

}

.diningbanner {
	width: 1000px;
	height: 50px;
	text-align: center;
}

.special{
    width: 300px;
    margin-left: 350px;
    margin-top: 70px;
}

.special_img{
	width: 100%;
}

.spLeft{
	clear: both;
	width: 500px;
}

.special_img{
	float: left;
	margin-bottom: 30px;
	margin-top: 30px;
}

.special_top{
	margin-bottom: 10px;

}

.spDown{
	float: left;
}

.spRight{
	float: left;
}


.gallery{
    width: 300px;
    margin-left: 400px;
    margin-top: 130px;
    margin-bottom: 30px;
}

</style>


</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="main-container" style="width: 100%">
	
		<div class="mainImg">       
	     	<img alt="" src="${pageContext.request.contextPath}/resource/images/main1.jpg">
        </div>
        
 		<a class="diningbanner" href="${pageContext.request.contextPath}/">       
	     	<img alt="" src="${pageContext.request.contextPath}/resource/images/dBanner.jpg" style="width: 955px; float: left;">
        </a>
    
	 	<div class="special">
	     	<img alt="" src="${pageContext.request.contextPath}/resource/images/special.gif" style="margin-bottom: 20px;">	 		
	 	</div>

       <div class="special_img">
			<div class="special_top">
				<img alt="" src="${pageContext.request.contextPath}/resource/images/special2.jpg" style=" width: 590px;height: 300px;">
				<img alt="" src="${pageContext.request.contextPath}/resource/images/special4.jpg" style=" width: 350px;margin-left: 10px;height: 300px;">
			</div>
			<div class="special_bottom">
		    	<img alt="" src="${pageContext.request.contextPath}/resource/images/special1.jpg" style=" width: 360px; float: left; height: 250px;" >
				<img alt="" src="${pageContext.request.contextPath}/resource/images/special3.jpg" style=" width: 275px; height: 250px; margin-left: 10px;">
				<img alt="" src="${pageContext.request.contextPath}/resource/images/special5.jpg" style="width: 300px; height: 250px; margin-left: 6px;"> 
		     </div>
	    </div>
     
     
	 	<div class="gallery">
	     	<img alt="" src="${pageContext.request.contextPath}/resource/images/gallery.gif" style="margin-top: 50px; margin-bottom: 20px;">	 		
	 	</div>
     
     
     <div style="width: 900px; text-align: center; margin-bottom: 120px;">
     <iframe width="960px" height="494" 
     src="https://www.youtube.com/embed/81xxCLvADlU" 
     title="YouTube video player" 
     frameborder="0" 
     allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
     allowfullscreen></iframe>
     </div>
     
     
     
     
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>
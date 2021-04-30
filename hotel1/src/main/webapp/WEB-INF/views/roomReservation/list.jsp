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
function calcNight1() {
	var f = document.getElementById("nights");
	var cIn = new Date(document.getElementById("checkIn").value);
	var cOut = new Date(document.getElementById("checkOut").value);
	
	if(document.getElementById("checkIn").value=="" || document.getElementById("checkOut").value=="") {
		f.innerHTML = 0;
		return;
	}
	
	f.innerHTML = (cOut.getTime()-cIn.getTime()) / (1000*60*60*24);
}
function calcNight() {
	var f = document.conditionForm.nights;
	var cIn = new Date(document.getElementById("checkIn").value);
	var cOut = new Date(document.getElementById("checkOut").value);
	
	if(document.getElementById("checkIn").value=="" || document.getElementById("checkOut").value=="") {
		f.innerHTML = 0;
		return;
	}
	
	f.value = (cOut.getTime()-cIn.getTime()) / (1000*60*60*24);
}
function searchList() {
	var f = document.conditionForm;
	f.action = "${pageContext.request.contextPath}/rr/list.do";
	f.submit();
}
function clickRsv(num) {
	var f = document.conditionForm
	var url = "${pageContext.request.contextPath}/rr/reserve.do?classNum="+num
			+ "&checkIn=${checkIn}&checkOut=${checkOut}&guestCount=${guestCount}&nights="+f.nights.value;
	location.href=url;
}
function detail(num) {
	location.href="${pageContext.request.contextPath}/rr/detail.do?num="+num;
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
			<li><a href="${pageContext.request.contextPath}/diningReserve/reservation.do">다이닝 예약</a></li>
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
            			<!-- <td align="center"> <span  id="nights">${nights}</span>박 </td>  -->
            			<td align="center"> <input name="nights" type="text" readonly="readonly" style="width: 30px; border: none; text-align: center;">박 </td>
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
        
        <table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
            <tr>
            	<td rowspan="4" width="320px">
            		<a href="javascript:void(0);" onclick="detail(1);">
            			<img src="${pageContext.request.contextPath}/resource/images/de/de1.jpg" width="250" height="115" >
            		</a>
            	</td>
                <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Deluxe
                </td>
            </tr>
            <tr>
                 <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 36㎡
                 </td>
            </tr>
            <tr>
                 <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈), 트윈
                 </td>
            </tr>
            <tr>
           		 <td style="padding-top: 12px;">
           		 	<a href="javascript:void(0);" onclick="detail(1);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a>
       		 	 </td>
            	 <td>
            	 	<input type="button" onclick="clickRsv(1);" class="btn btn-outline-primary" value="예약하기"  style="float:right">
            	 </td>
   	      	</tr>
         </table>
                 
 		<table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
            <tr>
                 <td rowspan="4" width="320px"><a href="javascript:void(0);" onclick="detail(2);" ><img src="${pageContext.request.contextPath}/resource/images/de/de2.jpg" width="250" height="115" ></a></td>
                 <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Business Deluxe </td>
            </tr>
               
                    <tr>
                       <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 43㎡</td>
                    </tr>
                    <tr>
                       <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈), 트윈</td>
                    </tr>
                       <tr>
                        <td style="padding-top: 12px;"><a href="javascript:void(0);" onclick="detail(2);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a></td>
              	      		<td><input type="button" onclick="clickRsv(2);" class="btn btn-outline-primary" value="예약하기"  style="float:right">		</td>
      				
              	      </tr>
                 </table>
                 
                 
                 
                 <table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
                    <tr>
                      <td rowspan="4" width="320px"><a href="javascript:void(0);" onclick="detail(3);"><img src="${pageContext.request.contextPath}/resource/images/grcode/grcode1.jpg" width="250" height="115" ></a></td>
               
                        <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Grand Corner Deluxe</td>
                  	
                    </tr>
               
                    <tr>
                       <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 51㎡</td>
                    </tr>
                    <tr>
                       <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈)</td>
                    </tr>
                       <tr>
                        <td style="padding-top: 12px;"><a href="javascript:void(0);" onclick="detail(3);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a></td>
              	       	<td><input type="button" onclick="clickRsv(3);" class="btn btn-outline-primary" value="예약하기"  style="float:right">		</td>
      				
              	      </tr>
                 </table>
                 
                 
                 <table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
                    <tr>
                         <td rowspan="4" width="320px"><a href="javascript:void(0);" onclick="detail(4);"><img src="${pageContext.request.contextPath}/resource/images/exgrde/exgr1.jpg" width="250" height="115" ></a></td>
                   <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Executive Grand Deluxe </td>
                 	
                    </tr>
               
                    <tr>
                       <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 53㎡</td>
                    </tr>
                    <tr>
                       <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈)</td>
                    </tr>
                       <tr>
                     <td style="padding-top: 12px;"><a href="javascript:void(0);" onclick="detail(4);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a></td>
              	  	      	<td><input type="button" onclick="clickRsv(4);" class="btn btn-outline-primary" value="예약하기"  style="float:right">		</td>
      				
              	      </tr>
                 </table>
                 
             
                 
                 <table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
                    <tr>
                            <td rowspan="4" width="320px"><a href="javascript:void(0);" onclick="detail(5);"><img src="${pageContext.request.contextPath}/resource/images/susu/ss1.jpg" width="250" height="115" ></a></td>
                
                       <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Superior Suite </td>
                 	
                    </tr>
               
                    <tr>
                       <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 66㎡</td>
                    </tr>
                    <tr>
                       <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈), 트윈</td>
                    </tr>
                       <tr>
                           <td style="padding-top: 12px;"><a href="javascript:void(0);" onclick="detail(5);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a></td>
              	     	<td><input type="button" onclick="clickRsv(5);" class="btn btn-outline-primary" value="예약하기"  style="float:right">		</td>
      					
              	      </tr>
                 </table>
                 
                 
                 
                 <table style="border-bottom: 1px solid #eee; width: 100%; padding: 20px 0;">
                  
                    <tr>
                  <td rowspan="4" width="320px"><a href="javascript:void(0);" onclick="detail(6);"><img src="${pageContext.request.contextPath}/resource/images/cosu/cs1.jpg" width="250" height="115" ></a></td>
                          <td style="font-size: 17px; padding-top:10px; padding-top10:10px; padding-bottom: 10px; color: #a1886f;">Corner Suite</td>
                 	
                    </tr>
               
                    <tr>
                       <td style="font-size: 12px; padding-top:10px; padding-bottom: 10px; ">객실크기 : 68㎡</td>
                    </tr>
                    <tr>
                       <td style="font-size: 12px; padding-top: 10px;">침대타입 : 더블(킹 사이즈), 트윈</td>
                    </tr>
                       <tr>
                     <td style="padding-top: 12px;"><a href="javascript:void(0);" onclick="detail(6);" style="font-size: 10px; color: #a1886f;">객실 자세히 보기</a></td>
              	   	   <td><input type="button" onclick="clickRsv(6);" class="btn btn-outline-primary" value="예약하기"  style="float:right">		</td>
              	      </tr>
             		
                 </table>     	
        
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>
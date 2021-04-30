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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/rsv/reserve.css" type="text/css">
<script type="text/javascript">
function isValidEmail(data){
    var format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}
function isValidPhone(data) {
    // var format = /^(\d+)-(\d+)-(\d+)$/;
    var format=/^(01[016789])-[0-9]{3,4}-[0-9]{4}$/g;
    return format.test(data);
}
function rsvSubmit() {
	var f = document.infoForm;
	var str;
	
	if(! f.seatType.value) {
		alert("좌석 유형을 선택해주세요.");
		f.seatType.focus();
		return;
	}
	
	if(! f.rsvDate.value) {
		alert("날짜를 선택해주세요.");
		f.rsvDate.focus();
		return;
	}
	
	str = f.guestCount.value;
	
	if(! /^\d{1,2}$/.test(str)) {
		alert("인원을 정확히 입력해주세요.");
		f.guestCount.focus();
		return;
	}
	
	if(! f.time.value) {
		alert("시간을 선택해 주세요.");
		f.time.focus();
		return;
	}
	
	str = f.firstName.value;

	if(! /^[가-힣]{1,5}|[a-zA-z\s]{2,10}$/.test(str)){
		alert("이름을 정확히 입력해주세요.");
			f.firstName.focus();
			return;			
	}
	
	str = f.lastName.value;
 
	if(! /^[가-힣]{1}|[a-zA-z\s]{2,10}$/.test(str)){
		alert("성을 정확히 입력해주세요.");
			f.lastName.focus();
			return;			
	}
	
	str = f.email.value;
	if(! isValidEmail(str)){
		alert("이메일을 정확히 입력해주세요.");
		f.email.focus();
		return;
	}
	
	if(! f.region.value) {
		alert("지역을 선택해주세요.");
		f.region.focus();
		return;
	}
	
	str = f.tel.value;
    if(! isValidPhone(str)) {
        alert("연락처를 정확히 입력해주세요. ");
        f.tel.focus();
        return;
    }
    
    if(! f.creditCorp.value) {
    	alert("카드종류를 선택해주세요.");
    	f.creditCorp.focus();
    	return;
    }
    
    if(! /^\d{4}$/.test(f.c1.value)) {
    	alert("카드번호를 정확히 입력해주세요.");
    	f.c1.focus();
    	return;
    }
    
    if(! /^\d{4}$/.test(f.c2.value)) {
    	alert("카드번호를 정확히 입력해주세요.");
    	f.c2.focus();
    	return;
    }
    
    if(! /^\d{4}$/.test(f.c3.value)) {
    	alert("카드번호를 정확히 입력해주세요.");
    	f.c3.focus();
    	return;
    }
    
    if(! /^\d{4}$/.test(f.c4.value)) {
    	alert("카드번호를 정확히 입려해주세요.");
    	f.c4.focus();
    	return;
    }
    
    if(! f.creditMonth.value) {
    	alert("유효기간을 입력해주세요.");
    	f.creditMonth.focus();
    	return;
    }
    
    if(! f.creditYear.value) {
    	alert("유효기간을 입력해주세요.");
    	f.creditYear.focus();
    	return;
    }
    
    if(! f.check.checked) {
    	alert("유의사항, 취소 및 환불 규정을 모두 확인하고 동의해주세요.");
    	return;
    } 
    
	f.action = "${pageContext.request.contextPath}/diningReserve/reserve_ok.do";
	f.submit();	
}

function loadMem() {
	var f = document.infoForm;
	var check = "${mdto.lastName}";
	if( ! check ) {
		f.mode.value = "memNone"		
		return;
	}
	
	f.mode.value = "mem";
	f.lastName.value = "${mdto.lastName}";
	f.firstName.value = "${mdto.firstName}";
	f.email.value = "${mdto.email}";
	f.tel.value = "${mdto.tel}";
	
	f.lastName.readOnly = true;
	f.firstName.readOnly = true;
	f.email.readOnly = true;
	f.tel.readOnly = true;
	
}
window.onload = function() {
		loadMem();
}
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container" style="width: 1200px;">
    <div class="body-container" style="width: 1200px;">
        <div class="body-title">
            <h3> 고객 정보 입력 </h3>
        </div>
        
        <div class="body-content">
        	<form name="infoForm" method="post">
        		<table style="width: 100%; background: #f0f0f0;">
					<tr class="table-title">
						<td class="table-title" align="left" >인원, 날짜, 시간 선택</td> 
						<td class="table-title" align="right" >
							<span class="red">*</span>&nbsp;&nbsp;표시 필수 입력사항
						</td> 
					</tr>
				</table> 
        		
        		<div class="left">
        			<table class="info-left">
        				<tr>
        					<td>좌석 유형<span class="red">*</span></td>
        					<td> 
	        					<select name="seatType" class="selectField">
	        						<option value="">선택</option>
	        						<option value="1">룸</option>
	        						<option value="1">테이블</option>
	        					</select>
        					</td>
        				</tr>
        				<tr>
        					<td>날짜<span class="red">*</span></td>
        					<td> <input name="rsvDate" class="boxTF"  type="date"> </td>
        				</tr>
        				<tr>
        					<td>인원<span class="red">*</span></td>
        					<td> <input name="guestCount" class="boxTF"  type="text"> </td>
        				</tr>
        				<tr>
        					<td>시간<span class="red">*</span></td>
        					<td> 
        						<select name="time" class="selectField" >
        							<option value="">선택</option>
        							<option>10:00</option>
        							<option>10:30</option>
        							<option>11:00</option>
        							<option>11:30</option>
        							<option>12:00</option>
        							<option>12:30</option>
        							<option>13:00</option>
        							<option>13:30</option>
        							<option>17:00</option>
        							<option>17:30</option>
        							<option>18:00</option>
        							<option>18:30</option>
        							<option>19:00</option>
        							<option>19:30</option>
        							<option>20:00</option>
        							<option>20:30</option>
        						</select>
        					</td>
        				</tr>
        			</table>
        		</div>
        		<div class="right" style="background: #f5f5f5; height: 197px">
        			
        		</div>
        		
        		
				<table style="width: 100%; background: #f0f0f0;">
					<tr class="table-title">
						<td class="table-title" align="left" >정보입력</td> 
						<td class="table-title" align="right" >
							<span class="red">*</span>&nbsp;&nbsp;표시 필수 입력사항
						</td> 
					</tr>
				</table>        	
        		<div class="left">
        			<table class="info-left">
        				<tr>
        					<td colspan="2" align="left" style="font-weight: bold; font-size: 120%;">고객정보</td>
        				</tr>
        				<tr>
        					<td>성명(name)<span class="red">*</span></td>
        					<td> 
	        					<input name="lastName" class="boxTF" type="text" placeholder="lastName">
	        					<input name="firstName" class="boxTF" type="text" placeholder="firstName">
        					</td>
        				</tr>
        				<tr>
        					<td>이메일<span class="red">*</span></td>
        					<td> <input name="email" class="boxTF"  type="text"> </td>
        				</tr>
        				<tr>
        					<td>연락처<span class="red">*</span></td>
        					<td> <input name="tel" class="boxTF"  type="text"> </td>
        				</tr>
        				<tr>
        					<td>지역(여권기준)<span class="red">*</span></td>
        					<td> 
        						<select name="region" class="selectField">
        							<option value="">선택</option>
        							<option>South Korea</option>
        							<option>Japan</option>
        							<option>China</option>
        							<option>U.S</option>
        							<option>Russia</option>
        						</select>
        					</td>
        				</tr>
        			</table>
        		</div>
        		<div class="right">
        			<table class="info-right">
        				<tr>
        					<td colspan="2" align="left" style="font-weight: bold; font-size: 120%;">신용카드 정보</td>
        				</tr>
        				<tr>
        					<td>카드종류<span class="red">*</span></td>
        					<td> 
	        					<select name="creditCorp" class="selectField">
	        						<option value="">선택</option>
	        						<option value="BC">BC CARD</option>
	        						<option value="CITIBANK">CITIBANK CARD</option>
	        						<option value="HUNDAI">HUNDAI CARD</option>
	        						<option value="KEB">KEB CARD</option>
	        						<option value="KOOKMIN">KOOKMIN CARD</option>
	        						<option value="LOTTE">LOTTE CARD</option>
	        						<option value="SHINHAN">SHINHAN CARD</option>
	        						<option value="SAMSUNG">SAMSUNG CARD</option>
	        					</select> 
        					</td>
        				</tr>
        				<tr>
        					<td>카드번호<span class="red">*</span></td>
        					<td>
        						<input name="c1" class="boxTF" type="text" maxlength="4"> - 
        						<input name="c2" class="boxTF" type="text" maxlength="4"> - 
        						<input name="c3" class="boxTF" type="text" maxlength="4"> - 
        						<input name="c4" class="boxTF" type="text" maxlength="4">
        					</td>
        				</tr>
        				<tr>
        					<td>유효기간<span class="red">*</span></td>
        					<td>
        						<select name="creditMonth" class="selectField">
        							<option value="">선택</option>
        							<option>01</option>
        							<option>02</option>
        							<option>03</option>
        							<option>04</option>
        							<option>05</option>
        							<option>06</option>
        							<option>07</option>
        							<option>08</option>
        							<option>09</option>
        							<option>10</option>
        							<option>11</option>
        							<option>12</option>
        						</select>
        						<select name="creditYear" class="selectField">
        							<option value="">선택</option>
        							<option>2031</option>
        							<option>2030</option>
        							<option>2029</option>
        							<option>2028</option>
        							<option>2027</option>
        							<option>2026</option>
        							<option>2025</option>
        							<option>2024</option>
        							<option>2023</option>
        							<option>2022</option>
        							<option>2021</option>
        						</select>
        					</td>
        				</tr>
        				<tr>
        					<td colspan="2" rowspan="4" style="font-size: 80%; color: #707070;" >
        					※ 신용카드 정보는 고객님의 투숙을 개런티하기 위한 용도 외에는 어떤 목적으로도 사용되지<br>
        					&nbsp;&nbsp;&nbsp;않으며, 체크카드 및 일부 신용 카드의 경우 사용이 제한 될 수 있습니다. <br>
        					※ <span style="color: red">온라인 예약 시 직접 결제가 이루어지지 않으며,</span> 최종 결제는 프론트 데스크에서 해주시기<br>
        					&nbsp;&nbsp;&nbsp;바랍니다.
        					</td>
        				</tr>
        			</table>
        				
        		</div>
        		<table style="width: 100%">
        			<tr>
        				<td class="table-title" align="left">유의사항</td> 
        			</tr>
        		</table>
        		<div class="left">
        			<ul class="hotel">
        				<li style="color: black; font-size: 120%">호텔 이용안내</li>
        				<li>- 요금에는 부가가치세 10% 및 봉사료 10%(합계 21%)가 부과됩니다.</li>
        				<li>
        				- 기준인원을 초과하여 투숙 시 추가 인원에 대한 별도의 요금이 부과됩니다.<br>
        				&nbsp;&nbsp;추가 인원에 대한 기본 요금(세금 및 봉사료 별도)은 성인 5만원, 어린이 5만원<br>&nbsp;&nbsp;이며, 객실 타입 및 패키지 혜택에 따라 상이합니다.<br>
        				&nbsp;&nbsp;(성인 기준 : 만 13세 이상, 어린이 기준 : 37개월 이상 ~ 만 12세 이하)
        				</li>
        				<li>- 37개월 미만의 유아 동반 시 추가 이누언 요금 및 조식은 무료이며, 유아(37개월 <br>&nbsp;&nbsp;미만) 동반 여부는 체크인 시 프런트 데스크 직원에게 알려 주셔야 무료로 이용 <br>&nbsp;&nbsp;가능합니다.</li>
        				<li>
        				- 체크인은 오후 3시부터이며 체크아웃은 정오 12시까지입니다.<br>
        				&nbsp;&nbsp;오후 3시 이전 Early Check-In 또는 12시 이후 Late Check-Out 하실 경우 추가<br>&nbsp;&nbsp;요금이 부과될 수 있습니다.
        				</li>
        				<li>
        				- 체크인 시 등록카드 작성 및 투숙객 본인 확인을 위해 본인 사진이 포함된 신분<br>&nbsp;&nbsp;증을 반드시 제시해 주시기 바랍니다.
        				</li>
        			</ul>
        		</div>
        		<div class="right">
        			<ul class="conv">
        				<li style="color: black; font-size: 120%">부대시설 이용안내</li>
        				<li>
        				- 체련장(Gym), 실내 수영장, 실내 사우나(유료시설)는 매월 3번째 수요일 정기 휴무입니다.<br>&nbsp;&nbsp;
        				(단, 2021년 5월은 3번째 수요일이 석가탄신일(공휴일)로 5월 26일이 정기 휴무일입니다.)
        				</li>
        				<li>
        				- 체련장은 만 16세 이상, 실내 사우나는 만 13세 이상부터 이용 가능합니다.
        				</li>
        				<li>
        				- 실내 수영장은 성인 고객 전용 시설로, 만 13세 미만 고객은 주말 및 공휴일에 한해 성인 보호자 <br>
        				&nbsp;&nbsp;의 보호 하에 이용 가능합니다.
        				</li>
        				<li>
						- 야외 수영장인 어번 아일랜드는 유료 시설로서 입장 혜택이 포함된 상품 외에는 이용 시 입장료<br>&nbsp;&nbsp;
						가 추가로 부과되며 사전 예약은 불가능합니다. 쾌적하고 안전한 운영을 위해 적정 인원 초과 시 <br>&nbsp;&nbsp;
						입장이 제한될 수 있습니다.        				
        				</li>
        				<li>
        				- 2021년 어번 아일랜드(야외 수영장) 운영 기간 : 3월 13일 ~ 11월 14일
        				</li>
        				<li>
        				- 실내 및 야외 수영장의 성인풀에서는 신장 140cm 미만인 고객은 성인 보호자의 보호 하에 구명<br>&nbsp;&nbsp;
        				조끼 착용 시에만 이용 가능합니다.
        				</li>
        			</ul>
        		</div>
        		<table style="width: 100%">
        			<tr>
        				<td class="table-title" align="left">취소 및 환불 규정</td> 
        			</tr>
        		</table>
        		<div class="refund">
        		<h3>[취소/변경] 및 노쇼(No-show) 안내]</h3>
        		숙박 예정일 1일 전18시까지는 위약금 없이 취소 및 변경이 가능합니다.<Br>
        		숙박 예정일 1일 전 18시 이후 취소/변경 및 노쇼(No-show) 발생 시,<Br>
        		- 성수기(5월~10월, 12월 24일~31일) : 최초 1일 숙박 요금의 80%가 위약금으로 부과됩니다.<Br>
        		- 비수기(성수기 외 기간) : 최초 1일 숙박 요금의 10%가 위약금으로 부과됩니다.
        		</div>
        		<table style="width: 100%">
        			<tr>
        				<td class="table-title" align="center"  style="background: #f1e3c4;">
        				<input name="check" id="rsvCheck" type="checkbox"><label for="rsvCheck"></label>
        				<input name="mode" type="hidden">
        				<input name="dinNum" type="hidden" value="${dinNum}">
        				 유의사항, 취소 및 환불 규정을 모두 확인했습니다.
        				</td> 
        			</tr>
        		</table>
        		<div class="btns">
					<button type="button" class="btnConfirm" onclick="rsvSubmit()">예약완료</button>        		
        		</div>
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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 입력 | THE Ezo Hotels</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/member.css" type="text/css">

<script type="text/javascript">

function memberOk() {
	var f = document.memberForm;
	var str;
	
	str = f.userId.value;
	
	if(!str){
		alert(" 아이디를 입력하세요.");
		f.userId.focus();
		return;
	}
	//5~12자 이내 영문 또는 영문/숫자 조합
	if(!/^\w{5,12}$/i.test(str)){
		alert("아이디는 5~12자 이내의 영문/숫자를 사용하세요.");
		f.userId.focus();
		return;
	}
	
	str = f.userPwd.value;

	if(! str){
		alert("비밀번호를 입력해 주세요.");
		f.userPwd.focus();
		return;
	}

	if(! /^(?=.*[a-z])(?=.*[~!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(f.userPwd.value)){
		alert("비밀번호는 5~10자 이내의 영문자와 하나이상의 숫자 또는 특수문자로 입력해 주세요.");
		f.userPwd.focus();
		return;
	}	

	if( f.userPwd.value != f.userPwdCheck.value){
		alert("비밀번호가 일치하지 않습니다.");
		f.userPwdCheck.focus();
		return;
	}

	str = f.firstName.value;

	if(! /^[가-힣]{2,5}|[a-zA-z]{2,10}\s[a-zA-z]{2,10}$/.test(str)){
		alert("이름을 정확히 입력해주세요.");
			f.firstName.focus();
			return;			
	}
	
	str = f.lastName.value;
 
	if(! /^[가-힣]{1}|[a-zA-z]{2,10}\s[a-zA-z]{2,10}$/.test(str)){
		alert("성을 정확히 입력해주세요.");
			f.lastName.focus();
			return;			
	}
	
	str = f.birth.value;
	if(!str || ! isValidDateFormat(str)){
		alert("형식에 맞춰 생년월일을 입력해주세요.[YYYY-MM-DD]");
		f.birth.focus();
		return;
	}	
	
	str = f.email1.value;
	if(! str){
		alert("이메일을 입력해주세요.");
		f.email1.focus();
		return;
	}
	
	str = f.email2.value;
	if(! str){
		alert("이메일을 입력해주세요.");
		f.email2.focus();
		return;
	}
	
    if(!/^\d{3}$/.test(f.tel1.value)) {
        alert("번호를 입력해주세요. ");
        f.tel1.focus();
        return;
    }			

	if(!/^\d{4}$/.test(f.tel2.value)) {
        alert("번호를 입력해주세요. ");
        f.tel2.focus();
        return;
    }	

	if(!/^\d{4}$/.test(f.tel3.value)) {
        alert("번호를 입력해주세요. ");
        f.tel3.focus();
        return;
    }	
	
	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
	f.submit();	
}

function changeEmail() {
	var f = document.memberForm;
	var str = f.selectEmail.value;
	if(str!="direct"){
        f.email2.value=str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value="";
        f.email2.readOnly = false;
        f.email1.focus();
    }

}


</script>

</head>
<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="container">
	<div class="body-container" style="width: 700px; margin-left: 50px;">
		<div class="body-title">
           <h3>${title}</h3>
		</div>
		
		<div>
			<form name="memberForm" method="post">
				<div class="idTitle">
					<h4>아이디, 비밀번호</h4>
				</div>				
				<table class="memTable">
					<tr class="col">
						<td class="tbName">
							아이디 
						</td>
						<td class="tbContent">
							<input type="text" name="userId" id="userId" value="${dto.userId}" style="padding: 3px;"
							maxlength="15" ${mode == "update" ? "readonly='readonly'" : "" }>
							<span>5~12자 이내 영문 또는 영문/숫자 조합</span>
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">
							비밀번호 
						</td>
						<td class="tbContent">
							<input type="password" name="userPwd" id="userPwd" style="padding: 3px;">
							<span>6~15자 이내 영문/숫자 조합(특수문자 가능)</span>
						</td>					
					</tr>
					<tr class="col">
						<td class="tbName">
							비밀번호 확인 
						</td>
						<td class="tbContent">
							<input type="password" name="userPwdCheck" id="userPwdCheck" style="padding: 3px;">
						</td>					
					</tr>
				</table>
				
				<div class="basic-title">
					<h4>기본 입력</h4>
				</div>
				<table class="memTable">
					<tr class="col">
						<td class="tbName">성명</td>
						<td class="tbContent">
							<span>First name(이름)</span>&nbsp;   
							<input type="text" name="firstName" id="firstName" value="${dto.firstName}" 
							style="padding: 3px; width: 130px;"
							${mode == "update" ? "readonly='readonly'" : "" }>&nbsp;
							<span>Last name(성)</span>&nbsp;						
							<input type="text" name="lastName" id="lastName" value="${dto.lastName}"
							 style="padding: 3px; width: 130px;"
							 ${mode == "update" ? "readonly='readonly'" : "" }>&nbsp;
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">생년월일</td>
						<td class="tbContent">
							<input type="text" name="birth" id="birth" value="${dto.birth}" style="padding: 3px;">
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">이메일</td>
						<td class="tbContent">
							<select name="selectEmail" onchange="changeEmail();" style="height: 26px; width: 100px;">								
								<option value="">선 택</option>
								<option value="naver.com" ${dto.email2 == "naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
								<option value="gmail.com" ${dto.email2 == "gmail.com" ? "selected='selected'" : ""}>구글 메일</option>
								<option value="daum.net" ${dto.email2 == "daum.net" ? "selected='selected'" : ""}>다음 메일</option>
								<option value="direct">직접입력</option>							
							</select>
							&nbsp;
							<input type="text" name="email1" id="email1" value="${dto.email1}" style="padding: 3px; width: 130px;">
							@
							<input type="text" name="email2" id="email2" value="${dto.email2}" readonly="readonly" style="padding:3px; width: 130px;">
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">휴대전화</td>
						<td class="tbContent">
							<input type="text" name="tel1" id="tel1" maxlength="3" value="${dto.tel1}" style="padding: 3px; width: 100px;">
							-
							<input type="text" name="tel2" id="tel2" maxlength="4" value="${dto.tel2}" style="padding: 3px; width: 100px;">
							-
							<input type="text" name="tel3" id="tel3" maxlength="4" value="${dto.tel3}" style="padding: 3px; width: 100px;">									
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">자택 주소</td>
						<td class="tbContent">
							<p><input type="text" name="zip" id="zip" value="${dto.zip}" readonly="readonly" style="padding: 3px; margin-top: 20px; margin-bottom: 5px;">
							<button type="button" class="btn" onclick="daumPost();" style="margin-left: 3px;">주소찾기</button></p>
							<input type="text" name="addr1" id="addr1" value="${dto.addr1}" style=" width: 250px; padding: 3px; margin-top: 5px; margin-bottom: 15px;">  
							<input type="text" name="addr2" id="addr2" value="${dto.addr2}" style="padding: 3px; margin-top: 5px; margin-bottom: 15px;">
						
						</td>
					</tr>
				</table>			
				
				<table style="width: 100%">
					<tr>
						<td align="center"> 
							<button type="button" name="sendBtn" class="btnm" onclick="memberOk();">${mode=="member"?"회원가입":"정보수정"}</button>
						</td>
					</tr>
					<tr height="30">
						<td align="center" style="color: brown;">${message}</td>
					</tr>
					
				</table>
			</form>
		</div>	
	</div>
	
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<script>
	function daumPost() {
		new daum.Postcode({
			oncomplete : function (data) {
				
				var fullAddr = '';
				var extraAddr = '';
				
				if(data.userSelectedType == 'R'){
					fullAddr = data.roadAddress;
				}else {
					fullAddr = data.jibunAddress;
				}
				
				if(data.userSelectedType == 'R'){
		               //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
					
				}
				
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();

			}
		}).open();
	}

</script>	
</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>
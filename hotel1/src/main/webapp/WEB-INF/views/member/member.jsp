<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/member.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/util.js"></script>
<script type="text/javascript">

function memberOk() {
	var f = document.memberForm;
	var str;
	
	str = f.userId.value;
	str = str.trim();
	
	if(!str){
		alert(" 아이디를 입력하세요.");
		f.userId.focus();
		return;
	}
	//5~12자 이내 영문 또는 영문/숫자 조합
	if(!/^\w{5,12}$/i.test(str)){
		alert("아이디는 5~12자 이내의 영문/숫자를 사용하세요.")
		f.userId.focus();
		return;
	}
	f.userId.value = str;
	
	
	str = f.userPwd.vlaue;
	str = str.trim();
	if(!str){
		alert(" 비밀번호를 입력하세요.");
		f.userPwd.focus();
		return;
	}
	//8~20자 이내 영문/숫자 조합(특수문자 가능) 
	if(!/^\w{8,20}$/i.test(str)){
		alert("비밀번호는 8~20자 이내로 입력해 주세요.")
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;
	
	if(str != f.userPwd.vlaue){
		alert("비밀번호가 일치하지 않습니다.")
		f.userPwdCheck.focus();
		return;
	}
	
	str = f.firstName.value;
	str = str.trim();
	if(!str){
		alert("이름을 입력하세요")
		f.firstName.focus();
		return;
	}

    f.firstName.value = str;
	
	str = f.lastName.value;
	str = str.trim();
	if(!str){
		alert("성을 입력하세요")
		f.lastName.focus();
		return;
	}

    f.lastName.value = str;	
    
	str = f.birth.value;
	str = str.trim();
	if(!str){
		alert("생년월일을 입력하세요[YYYY-MM-DD]");
		f.birth.focus();
		return;
	}
	
	str = f.email1.value;
	str = str.trim();
	if(!str){
		alert("이메일을 입력하세요.");
		f.email1.focus();
		return;
	}
	
	str = f.email2.value;
	str = str.trim();
	if(!str){
		alert("이메일을 입력하세요.");
		f.email2.focus();
		return;
	}
	
	str = f.tel1.value;
	str = str.trim();
	if(!str){
		alert("전화번호를 입력하세요.");
		f.tel1.focus();
		return;
	}
	
    if(!/^(\d{3})$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel1.focus();
        return;
    }	
		
	str = f.tel2.value;
	str = str.trim();
	if(!str){
		alert("전화번호를 입력하세요.");
		f.tel2.focus();
		return;
	}

	if(!/^(\d{3})$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }	
		
	str = f.tel3.value;
	str = str.trim();
	if(!str){
		alert("전화번호를 입력하세요.");
		f.tel3.focus();
		return;
	}	
	
	if(!/^(\d{3})$/.test(str)) {
        alert("숫자만 가능합니다. ");
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
	<div class="body-container" style="width: 700px;">
		<div class="body-title">
           <h3><span style="font-family: Webdings">2</span>${title}</h3>
		</div>
		
		<div>
			<form name="memberForm" method="post">
				<div class="idTitle">
				<div class="idTitle-left">
					<h4>아이디, 비밀번호</h4>
				</div>
				<div class="idTitle-right">
				*표시 필수 입력
				</div>
				</div>				
				<table class="memTable">
					<tr class="col">
						<td class="tbName">
							아이디 
						</td>
						<td class="tbContent">
							<input type="text" name="userId" id="userId" value="${dto.userId}">
							<span>5~12자 이내 영문 또는 영문/숫자 조합</span>
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">
							비밀번호 
						</td>
						<td class="tbContent">
							<input type="text" name="userPwd" id="userPwd" value="${dto.userPwd}">
							<span>8~20자 이내 영문/숫자 조합(특수문자 입력 가능)</span>
						</td>					
					</tr>
					<tr class="col">
						<td class="tbName">
							비밀번호 확인 
						</td>
						<td class="tbContent">
							<input type="text" name="userPwdCheck" id="userPwdCheck">
						</td>					
					</tr>
				</table>
				
				<div class="basic-title">
					<h4>기본 입력</h4>
				</div>
				<table  class="memTable">
					<tr class="col">
						<td class="tbName">성명</td>
						<td class="tbContent">
							<span>First name(이름)</span>&nbsp;   
							<input type="text" name="firstName" id="firstName" value="${dto.firstName}">&nbsp;
							<span>Last name(성)</span>&nbsp;						
							<input type="text" name="lastName" id="lastName" value="${dto.lastName}">&nbsp;
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">생년월일</td>
						<td class="tbContent">
							<input type="text" name="birth" id="birth" value="${dto.birth}">
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">이메일</td>
						<td class="tbContent">
							<select name="selectEmail">
								<option value="">선 택</option>
								<option value="naver.com" ${dto.email2 == "naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
								<option value="gmail.com" ${dto.email2 == "gmail.com" ? "selected='selected'" : ""}>구글 메일</option>
								<option value="daum.net" ${dto.email2 == "daum.net" ? "selected='selected'" : ""}>다음 메일</option>
								<option value="direct">직접입력</option>							
							</select>

							<input type="text" name="email" id="email1" value="${dto.email1}">
							@
							<input type="text" name="email" id="email2" value="${dto.email2}" readonly="readonly">
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">휴대전화</td>
						<td class="tbContent">
							<input type="text" name="tel1" id="tel1" value="${dto.tel1}">
							-
							<input type="text" name="tel2" id="tel2" value="${dto.tel2}">
							-
							<input type="text" name="tel3" id="tel3" value="${dto.tel3}">									
						</td>
					</tr>
					<tr class="col">
						<td class="tbName">자택 주소</td>
						<td class="tbContent">
							<p><input type="text" name="zip" id="zip" value="${dto.zip}" readonly="readonly">
							<button type="button" class="btn" onclick="daumPost();">주소찾기</button></p>
							<input type="text" name="addr1" id="addr1" value="${dto.addr1}">  
							<input type="text" name="addr2" id="addr2" value="${dto.addr2}">
						
						</td>
					</tr>
				</table>			
				
				<table style="width: 100%">
					<tr>
						<td align="center"> 
							<button type="button" name="sendBtn" class="btnm" onclick="memberOk();">${mode=="member"?"회원가입":"정보수정"}</button>
							<c:if test="${mode=='delete'}">
							<button type="reset" class="btnm">회원탈퇴</button>							
							</c:if>
						</td>
					</tr>
					<tr>
						<td>${message}</td>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>
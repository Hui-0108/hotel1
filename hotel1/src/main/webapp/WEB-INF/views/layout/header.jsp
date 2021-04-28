<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,700&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Oswald:wght@200&family=Playfair+Display:ital,wght@1,700&display=swap" rel="stylesheet">
<script type="text/javascript">

$(function(){
	   $("input").not($(":button")).keypress(function (evt) {
	        if (evt.keyCode == 13) {
	            var fields = $(this).parents('form,body').find('button,input,textarea,select');
	            var index = fields.index(this);
	            if ( index > -1 && ( index + 1 ) < fields.length ) {
	                fields.eq( index + 1 ).focus();
	            }
	            return false;
	        }
	     });
});
</script>
<div class="header">
	<div class="header-top">
	    <div class="header-left">
	        <p>
	            <a href="${pageContext.request.contextPath}/">
	                <span class="title">HOTEL EZO</span>
	            </a>
	        </p>
	    </div>
	    <div class="header-right">
	        <div>
	            <c:if test="${empty sessionScope.member}">
	                <a class="member" href="${pageContext.request.contextPath}/member/login.do">로그인</a>
	                <span class="member">&nbsp;|&nbsp;</span>
	                <a class="member" href="${pageContext.request.contextPath}/member/member.do">회원가입</a>
	            </c:if>
	            <c:if test="${not empty sessionScope.member}">
	                <span class="member" style="font-weight: bold;">${sessionScope.member.lastName}</span>님
	                    <span class="member">&nbsp;|&nbsp;</span>
	                    <a class="member" href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
	                    <span class="member">&nbsp;|&nbsp;</span>
	                    <a class="member" href="${pageContext.request.contextPath}/member/pwd.do?mode=update">마이페이지</a>
	            </c:if>
	        </div>
	    </div>
	</div>
	
	<div class="menu">
	    <ul class="nav">
	        <li>
	            <a href="${pageContext.request.contextPath}/room/roomlist.do">예약하기</a>
	        </li>	
	        <li>
	            <a href="${pageContext.request.contextPath}">예약확인</a>
	        </li>
	        <li>
	            <a href="${pageContext.request.contextPath}/qna/list.do">고객문의</a>
	        </li>
	        <li>
	            <a href="${pageContext.request.contextPath}/pack/list.do">프로모션</a>
	        </li>
	    </ul>      
	</div>
</div>
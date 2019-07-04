<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../inc/adminTop.jsp" %>
<style type="text/css">
	#saveId ~ label{
		font-size: 0.9em;
	}
	.simpleForm form{
		width:410px;
	}
	.error{
		color: red;
		display: none;
	}
</style>
<Script type="text/javascript">
	$(function(){
		$('#userid').focus();
		
		$('.simpleForm input[type=submit]').click(function(){
			$('.infobox').each(function(){
				if($(this).val().length<1){
					$(this).next().show();
					event.preventDefault();
				}else{
					$(this).parent().find('.error').hide();
				}
			});
		});
	});
</Script>

<article class="simpleForm">
	<form name="frmLogin" method="post" 
		action="<c:url value='/admin/login/adminLogin.do'/>">
		<fieldset>
			<legend>관리자 로그인</legend>
			<div>
				<label for="userid" class="label">아이디</label>
				<input type="text" name="userid" id="userid" class="infobox"
					value="${cookie.ck_admin_userid.value}">
				<span class="error">아이디를 입력하세요</span>
			</div>
			<div>
				<label for="pwd" class="label">비밀번호</label>
				<input type="password" name="pwd" id="pwd" class="infobox">
				<span class="error">비밀번호를 입력하세요</span>
			</div>
			<div class="align_center">
				<input type="submit" value="로그인">
				<input type="checkbox" name="saveId" id="saveId"
					<c:if test="${!empty cookie.ck_admin_userid }">
						checked="checked"
					</c:if> >
				<label for="saveId">아이디 저장하기</label>
			</div>
		</fieldset>
	</form>
</article>
<%@include file="../../inc/adminBottom.jsp" %>





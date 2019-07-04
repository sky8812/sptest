<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>

<style type="text/css">
	.divAgree{
		width:700px;
	}

</style>
<script type="text/javascript">
	$(function(){
		$('form[name=frmAgree]').submit(function(){
			if(!$('input[name=agree]').is(':checked')){
				alert('약관에 동의하셔야 합니다.');
				event.preventDefault();
				$('#agree').focus();
				return false;
			}
			
		});
	});		
		
</script>
<h2>회원약관</h2>
<div class="divAgree">
<iframe src="<c:url value='/inc2/provision.html'/>" width="700px" height="300px"></iframe>
<form action="<c:url value='/member/register.do'/>" method="post" name="frmAgree">
	<div class="align_right">
	<input type="checkbox" name="agree" id="agree">
	<label for="agree">약관에 동의합니다.</label>
	</div>
	<div class="align_center">
		<input type="submit" value="확인">
		<input type="reset" value="취소">
	</div>
</form>

</div>
<%@ include file="../inc/bottom.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript" src="../js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	
</script>

<style type="text/css">
	.width_80{
		width:80px;
	}
	.width_350{
		width:350px;
	}	
</style>
<article>
<div class="divForm">
<form name="frm1" method="post" action="register2_ok.jsp">
<fieldset>
	<legend>회원 가입</legend>
    <div>        
        <label for="name">성명</label>
        <input type="text" name="name" id="name" style="ime-mode:active">
    </div>
    <div>
        <label for="userid">회원ID</label>
        <input type="text" name="userid" id="userid"
        		style="ime-mode:inactive">&nbsp;
        <input type="button" value="중복확인" id="btnChkId" title="새창열림">
    </div>
    <div>
        <label for="pwd">비밀번호</label>
        <input type="Password" name="pwd" id="pwd">
    </div>
    <div>
        <label for="pwd2">비밀번호 확인</label>
        <input type="Password" name="pwd2" id="pwd2">
    </div>
    <div>
        <label for="zipcode">주소</label>
        <input type="text" name="zipcode" id="zipcode" ReadOnly  
        	title="우편번호">
        <input type="Button" value="우편번호 찾기" id="btnZipcode" title="새창열림"><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="address" ReadOnly title="주소"  class="width_350"><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="addressDetail" title="상세주소"  class="width_350">
    </div>
    <div>
        <label for="hp">핸드폰</label>
        <input type="text" name="hp" id="hp" title="휴대폰">
    </div>
    <div>
        <label for="email1">이메일 주소</label>
        <input type="text" name="email"  id="email" title="이메일주소">
    </div>
    <div class="center">
         <input type="submit" id="wr_submit" value="회원가입">
    </div>
</fieldset>

    <input type ="hidden" name="chkId" id="chkId">
        
</form>
</div>
</article>

<%@ include file="../inc/bottom.jsp"%>












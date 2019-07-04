<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/mainstyle.css'/>">
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
<style type="text/css">
	h1{
		font-size: 1.5em;
		margin-bottom: 25px;
	}
</style>
<script type="text/javascript">
	$(function(){
		$('#btUse').click(function(){
			/*
			opener.document.frm1.userid.value='${param.userid}';
			opener.document.frm1.chkId.value='Y';
			opener.document.frm1.userid.readonly=true;
			*/
			
			$(opener.document).find("#userid").val("${param.userid}");
			$(opener.document).find("#chkId").val("Y");
			$(opener.document).find("#userid").prop("readOnly",true);
			self.close();
		});
	});
</script>
</head>
<body>
	<h1>아이디 중복검사</h1>
	<form name="frmChk" method="post" action="<c:url value='/member/checkUserid.do'/>">
		<label>아이디 : </label>
		<input type="text" name="userid" value="${param.userid }" readonly="readonly">
		<input type="submit" value="아이디확인">
		
		<c:if test="${result==NON_USEFUL_USERID }">
			<p>이미 등록된 아이디입니다. 다른 아이디를 입력하세요</p>
		</c:if>
		<c:if test="${result==USEFUL_USERID }">
			<input type="button" id="btUse" value="사용하기">
			<br>
			<p>사용가능한 아이디입니다. [사용하기]버튼을 클릭하세요</p>
		</c:if>

	</form>	
</body>
</html>










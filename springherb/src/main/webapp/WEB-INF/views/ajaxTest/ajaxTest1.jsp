<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajaxTest1.jsp</title>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"<c:url value='/ajaxTest/search.do'/>",
			type:"get",
			//data:"keyword=s&id=hong",
			data:{keyword:"s",
				id:"kim"},
			success:function(res){
				$('#info').html(res);
			},
			error:function(xhr,status, error){
				alert(status+":"+error);
			}
		});
	});
</script>
</head>
<body>
	<h1>ajax연습1</h1>
	
	<div id="info"></div>
</body>
</html>
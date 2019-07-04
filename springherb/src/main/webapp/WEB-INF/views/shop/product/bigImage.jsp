<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#divBig{
		text-align: center;
	}
	
	#divBig img{
	width:400px;
	}
</style>

</head>
<body>
	<div id="divBig">
		<img src="<c:url value='/pd_images/${param.imageURL }'/>">
		<br>
		<input type="button" value="닫기">
	</div>
</body>
</html>
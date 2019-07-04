<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../inc/top.jsp" %>
<style type="text/css">
  #divEvent{
    	width:780px;
    	margin:20px 0 0 0;
    }
    
    #divPd{
    	width:170px;
    	border:1px solid lightgray;
    	float:left;
    	text-align:center;
    	margin:0 10px 10px 0;
    	padding: 10px 0;
    }
    #divPd img{
    	width: 90px;
    }
</style>

		${param.categoryName } 목록
<div id="divEvent">
	<c:forEach var="vo" items="${list }">
		<div id="divPd">
		<a href="<c:url value='/shop/product/productDetail.do?productNo=${vo.productNo }'/>">
			<img src="${pageContext.request.contextPath }/pd_images/${vo.imageURL}" alt="${vo.productName }"><br>
			${vo.productName }</a><br>
			<fmt:formatNumber value="${vo.sellPrice }" pattern="#,###"/>원
		</div>
	</c:forEach>
</div>
<%@include file="../../inc/bottom.jsp" %>

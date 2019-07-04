<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="inc/top.jsp"%>
<article>
	<div id="centerCon">
		<img alt="허브 이미지" src="${pageContext.request.contextPath }/resources/images/herb.JPG">
	</div>
	<div id="rightCon">
		<c:import url="/board/mainNotice.do"></c:import>
	</div>
	<div id="listCon">
		<c:import url="shop/product/productCatalog.jsp"></c:import>
	</div>
</article>
<%@include file="inc/bottom.jsp"%>
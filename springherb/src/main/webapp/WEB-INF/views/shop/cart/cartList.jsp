<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp" %>
<style type="text/css">
	#divCart{
		width: 700px;
	}
	#divCart table{
		width: 700px;
	}
</style>
<script type="text/javascript">
	
</script>

<h2>장바구니</h2>
<div id="divCart">
<table class="box2" 
	summary="장바구니 목록에 관한 표로써 상품명,가격,수량,금액 등의 정보를 제공합니다.">
	<colgroup>
		<col style="width:40%">
		<col style="width:13%">
		<col style="width:20%">
		<col style="width:13%">
		<col style="width:*">		
	</colgroup>
	
	<thead>
		<tr>
			<th scope="col">상품명</th>
			<th scope="col">가격</th>
			<th scope="col">수량</th>
			<th scope="col">금액</th>
			<th scope="col">삭제</th>
		</tr>	
	</thead>
	<tbody>
		<c:if test="${empty list }">
			<tr class="align_center">
				<td colspan="5">장바구니가 비었습니다.</td>			
			</tr>
		</c:if>
		<c:if test="${!empty list }">
		<c:set var="buyPrice" value="0"/> <!-- 총 구매금액 -->
		<c:set var="delivery" value="0"/> <!-- 배송비 -->
		<c:set var="totalPrice" value="0"/> <!-- 총 주문합계 -->
		
			<!--반복 시작 -->
			<c:forEach var="map" items="${list }">
				<c:set var="sum" value="${map['SELLPRICE']*map['QUANTITY'] }"/>
			<tr>
				<td><img alt="${map['PRODUCTNAME'] }" width="40" align="absmiddle"
				src="<c:url value='/pd_images/${map["IMAGEURL"] }'/>">
				${map['PRODUCTNAME'] } 
				</td>		
				<td class="align_right"><fmt:formatNumber pattern="#,###" value="${map['SELLPRICE'] }"/> 원</td>	
				<td class="align_center">
					<form name="frmCart" method="post">
						<input type="text" size="4" name="quantity" value="${map['QUANTITY'] }">
						<input type="submit" value="수정">
					</form>
				</td>
				<td class="align_right"><fmt:formatNumber pattern="#,###" value="${sum }"/> 원</td>
				<td class="align_center"><a href="">삭제</a></td>
			</tr>
			<c:set var="buyPrice" value="${buyPrice+sum }"/> <!-- 총 구매금액 -->
		
			</c:forEach>
		<!--반복 끝 -->
		<!-- 총 구매금액이 3만원 미만이면 배송비 3000원 -->
			<c:if test="${buyPrice<BUY_PRICE }"> 
				<c:set var="delivery" value="${DELIVERY }"/> <!-- 배송비 -->
			</c:if>
			<c:set var="totalPrice" value="${delivery+buyPrice }"/> <!-- 총 주문합계 -->
		
			<tr>
				<td colspan="4" class="align_right" style="border-right: none">
					총 구매금액 : <br>
					배송비 : 		<br>
					총 주문합계 : 
				</td>
				<td class="align_right" style="border-left: none">
					<fmt:formatNumber pattern="#,###" value="${buyPrice }"/> 원<br>
					<fmt:formatNumber pattern="#,###" value="${delivery }"/> 원<br>
					<fmt:formatNumber pattern="#,###" value="${totalPrice }"/> 원
				</td>
			</tr>
		</c:if>
	</tbody>
</table>

	<div class="align_center" style="margin:10px 0">
	<c:if test="${!empty list }">
		<a href="<c:url value='/shop/order/orderSheet.do'/>">[주문하기]</a>
	</c:if>
		<a href="<c:url value='/index.do'/>">[계속 쇼핑하기]</a>
	</div>	
</div>

<%@ include file="../../inc/bottom.jsp" %>
















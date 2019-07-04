<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css">
	.cartListTbl{
		width:650px;		
	}
	.cartListTbl td{
		padding:10px;
	}
	.cartListTbl caption{
		visibility:hidden;
	}
	.cartListDiv1{
		width:650px;
		text-align:center;		
		margin:10px;
	}
	
	
	.divForm fieldset	{
		width: 100%;
	}
	.divForm span{
		font-size:1.0em; 
	}	
	.divForm legend	{
		visibility:hidden;
		font-size:0.1em;
		}	
	.divForm label, .divForm .sp1	{
		text-align: left;
	}
	.divForm .title{
		font-size:1.1em;
		font-weight:bold;	
	}
	.divForm .titleP, .titleP{
		margin:10px 0;	
		border: none;
	}
	.divForm .lbl{
		float: none;
		text-align: left;
		padding: 3px 0;
		clear: both;		
		font-weight: bold;
		}
		.divForm p	{
		clear: both;
		border-bottom: 1px solid #eee;
		padding: 5px 0;
		margin: 0;
		overflow: auto;
		}
</style>

<script type="text/javascript">
	$(function(){
		$.setAddress();		
		$("input[name=delivery]").click(function(){
			if($("#delivery1").is(":checked")){
				$.setAddress();		
			}else if($('#delivery2').is(":checked")){
				$.clearAddress();
			}
		});
		
		$('#btZipcode').click(function(){
			//우편번호 찾기 창 띄우기
			//window.open("url", "name", "option");
			window.open(
					"${pageContext.request.contextPath}/member/zipcode.do", 
					"zipWin", 
				"left=50, top=20, width=500, height=500, scrollbars=yes,resizable=yes");
		});
		
		$('input[type=submit]').click(function(){
			
			$('.infobox').each(function(){
				if($(this).val()==''){
					var msg='';
					
					if($(this).attr('name')=='addressDetail'){
						msg=$(this).attr('title');
					}else{
						msg=$(this).prev().html();
					}
					alert(msg+"을 입력해주세요");
					event.preventDefault();
					return false;
				}
			});
		});
		
	});
	
	$.setAddress=function(){
		
		$('#customerName').val($('#oName').html());
		$('#zipcode').val($('#oZipcode').html());
		$('input[name=address]').val($('#oAddress1').html());
		$('input[name=addressDetail]').val($('#oAddress2').html());
		var hp1=$('#oHp1').html();
		var hp2=$('#oHp2').html();
		var hp3=$('#oHp3').html();
		var hp=hp1+"-"+hp2+"-"+hp3;
		if(hp1!=''){
			$('#hp').val(hp);
		}
	}
	
	$.clearAddress=function(){
		$('#customerName').val('');
		$('#zipcode').val('');
		$('input[name=address]').val('');
		$('input[name=addressDetail]').val('');
		$('#hp').val('');
	}
	
	
	
	
</script>
<p class="titleP">  
	<img src="${pageContext.request.contextPath}/resources/images/dotLong3.JPG" align="absmiddle" />
    <span style="font-size:13pt;font-weight:bold">주문서 작성</span>
</p>  

<div>
    <table class="cartListTbl box2" 
	summary="장바구니 목록에 관한 표로써, 상품명,가격, 수량, 금액 등의 정보를 제공합니다.">
	<caption>장바구니 목록</caption>
	<colgroup>
		<col width="49%" />
		<col width="17%" />
		<col width="17%" />
		<col width="*" />		
	</colgroup>
	<thead>
		<tr>
			<th scope="col">상품명</th>
			<th scope="col">가격</th>
			<th scope="col">수량</th>
			<th scope="col">금액</th>			
		</tr>
	</thead>
	<tbody>
	
		<!--반복 시작 -->	
			<c:if test="${empty cartList }">
			<tr class="align_center">
				<td colspan="5">장바구니가 비었습니다.</td>			
			</tr>
		</c:if>
		<c:if test="${!empty cartList }">
		<c:set var="buyPrice" value="0"/> <!-- 총 구매금액 -->
		<c:set var="delivery" value="0"/> <!-- 배송비 -->
		<c:set var="totalPrice" value="0"/> <!-- 총 주문합계 -->
		
			<!--반복 시작 -->
			<c:forEach var="map" items="${cartList }">
				<c:set var="sum" value="${map['SELLPRICE']*map['QUANTITY'] }"/>
			<tr>
				<td><img alt="${map['PRODUCTNAME'] }" width="40" align="absmiddle"
				src="<c:url value='/pd_images/${map["IMAGEURL"] }'/>">
				${map['PRODUCTNAME'] } 
				</td>		
				<td class="align_right"><fmt:formatNumber pattern="#,###" value="${map['SELLPRICE'] }"/> 원</td>	
				<td class="align_center">
						${map['QUANTITY'] }
				</td>
				<td class="align_right"><fmt:formatNumber pattern="#,###" value="${sum }"/> 원</td>
			</tr>
			<c:set var="buyPrice" value="${buyPrice+sum }"/> <!-- 총 구매금액 -->
		
			</c:forEach>
		<!--반복 끝 -->
		<!-- 총 구매금액이 3만원 미만이면 배송비 3000원 -->
			<c:if test="${buyPrice<BUY_PRICE }"> 
				<c:set var="delivery" value="${DELIVERY }"/> <!-- 배송비 -->
			</c:if>
			<c:set var="totalPrice" value="${delivery+buyPrice }"/> <!-- 총 주문합계 -->
		
		</c:if>
		<!-- 반복 끝 -->
		
		
		<tr>
			<td colspan="3" align="right" style="border-right:none">
				총 구매금액 : <br>
				   + 배송비 : <br>
				총 주문합계 :    
			</td>
			<td align="right" style="border-left:none">
				<fmt:formatNumber pattern="#,###" value="${buyPrice }"/> 원<br>
					<fmt:formatNumber pattern="#,###" value="${delivery }"/> 원<br>
					<fmt:formatNumber pattern="#,###" value="${totalPrice }"/> 원
			</td>
		</tr>

	</tbody>
</table>
</div>       
<br />
<div class="divForm">    
	<form name="frm1" method="post" action="<c:url value='/shop/order/orderSheet.do'/>" >
	<fieldset>
		<legend>상품 받으시는 분</legend>

		<p class="titleP">
	    	<img src="${pageContext.request.contextPath}/resources/images/dot7.JPG" align="absmiddle" />
	    	<span class="title">주문하시는 분</span>
	    </p>
    
       <p><span class="sp1">이름</span>
         <span id="oName" >${memberVo.name }</span>
	   </p>
       <p>
           <span class="sp1">주소</span>
           <span id="oZipcode">${memberVo.zipcode }</span>
           <span id="oAddress1">${memberVo.address }</span>
           <span id="oAddress2">${memberVo.addressDetail }</span>
       </p>
       <p>
           <span class="sp1">연락처</span>
           <c:if test="${!empty memberVo.hp1 }">
           <span id="oHp1">${memberVo.hp1 }</span>
           -<span id="oHp2">${memberVo.hp2 }</span>
           -<span id="oHp3">${memberVo.hp3 }</span>
           </c:if>
		</p>
       <p>
           <span class="sp1">이메일</span>
           <span>${memberVo.email1 }</span>
           <c:if test="${!empty memberVo.email2 }">
           @
           </c:if>
           <span>${memberVo.email2 }</span>
       </p>
    
    	<br /> 
	    <p class="titleP">
	    	<img src="${pageContext.request.contextPath}/resources/images/dot7.JPG" align="absmiddle" />
	    	<span class="title">상품 받으시는 분</span>
	    </p>	
	    <p>        
	        <span class="sp1">배송지 선택</span>    	       
	        <input type="radio" name="delivery" id="delivery1" checked> 
	        <label for="delivery1" class="lbl">주문고객과 동일 주소</label>                 
	        <input type="radio" name="delivery"	id="delivery2"> 
	        <label for="delivery2" class="lbl">새로운 주소 입력</label>
	    </p>
        <p>
            <label for="customerName">성명</label> 
            <input type="Text" name="customerName" id="customerName" class="infobox">
        </p>
        <p>
            <label for="zipcode">주소</label>                           
            <input type="Text" name="zipcode" id="zipcode" size="15" title="우편번호" readOnly class="infobox">
&nbsp;		<input type="Button" value="우편번호찾기" id="btZipcode"/>
            <br />
            <span class="sp1">&nbsp;</span>
            <input type="Text" name="address"  size="60" title="주소" readonly>
            <br />
            <span class="sp1">&nbsp;</span>
            <input type="Text" name="addressDetail"  size="60" title="상세주소" class="infobox">           
        </p>
        <p>
            <label for="hp">연락처</label>
            <input type="Text" name="hp" id="hp" size="17" class="infobox">
        </p>
        <p>
            <label for="message">배송시 요청사항</label>
                <textarea name="message" id="message" cols="82" rows="3" ></textarea>
        </p>    
	
    <br />
    <p class="titleP">
    	<img src="${pageContext.request.contextPath}/resources/images/dot7.JPG" align="absmiddle" />
    	<span class="title">결제 정보</span>
    </p>	
    <p>
        <span class="sp1">결제금액</span>
        <span>${totalPrice }원</span>
    </p>
    <p class="center">
        <input type="submit" value="결제하기"  />
    </p>
    
    <!-- 주문 총 금액 hidden field -->
    <input type="hidden" name="totalPrice" value="${totalPrice }" >
    </fieldset>
</form>

</div>
    
<%@ include file="../../inc/bottom.jsp"%>    
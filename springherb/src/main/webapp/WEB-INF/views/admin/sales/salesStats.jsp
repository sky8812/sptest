<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../../inc/adminTop.jsp" %>
<c:set var="salesFlag" value="${param.type }"/>
<c:set var="salesTitle" value="${salesFlag=='d'?'일별':salesFlag=='m'?'월별':'기간별' }" />
<c:set var="salesHeader" value="${salesFlag=='m'?'매출월':'매출일' }"/>

<style type="text/css">
    #divSales table{
    	width:500px;
    }
    #divSales{
    	margin-top:20px;
    }
</style>
<script type="text/javascript">
	$(function(){
		$.setYear();
		$.setMonth();
		
		$('#frmStats').submit(function(){
			
			var salesFlag="${salesFlag}";
			if(salesFlag=='m'){
					if($('#year option:selected').val()==''){
						alert('년도를 선택해주세요');
						event.preventDefault();
						return false;
					}
			}else if(salesFlag=='d'){
				if($('#year option:selected').val()==''){
					alert('년도를 선택해주세요');
					event.preventDefault();
					return false;
				}else if($('#month option:selected').val()==''){
					alert('월별 날짜를 선택해주세요');
					event.preventDefault();
					return false;
				}
			}else if(salesFlag=='t'){
				if($('#startDay').val()==''){
					alert('시작일을 선택해주세요');
					event.preventDefault();
					return false;
				}else if($('#endDay').val()==''){
					alert('endDay를 선택해주세요');
					event.preventDefault();
					return false;
				}
			}
		});
		
	}); 
	
	$.setYear=function(){
		var d=new Date();
		var year=d.getFullYear();
		
		for(var i=year-5;i<=year;i++){ //2014~2019
			var opt="";
			if(i=="${param.year}"){
				opt="<option value='"+i+"' selected>"+i+"</option>";
				
			}else{
				opt="<option value='"+i+"'>"+i+"</option>";
			}
			$('#year').append(opt);
		}
	}
	
	$.setMonth=function(){
		for(var i=1;i<=12;i++){
			var opt="";
			if(i=="${param.month}"){
				opt="<option value='"+i+"' selected>"+i+"</option>";
				
			}else{
				opt="<option value='"+i+"'>"+i+"</option>";
				
			}
			$('#month').append(opt);
		}
	}
	
</script>

    <h2>${salesTitle } 매출</h2>
    <form action="<c:url value='/admin/sales/salesStats.do?type=${salesFlag }'/>" id="frmStats" method="post">
    <c:if test="${salesFlag!='t' }">
    	<select name="year" id="year">
    		<option value="">선택하세요</option>
    	</select> <label for="year">년</label>
    	<c:if test="${salesFlag=='d' }">
	    	<select name="month" id="month">
	    		<option value="">선택하세요</option>
	    	</select> <label for="month">월</label>
    	</c:if>
    </c:if>
    <c:if test="${salesFlag=='t' }">
    	<%@include file="../../inc/dateTerm.jsp"  %>
    </c:if>
    	<input type="submit" value="매출 조회">
    </form>
    
    <div id="divSales">
    <table class="box2" summary="매출에 관한 표로서 매출일, 매출액에 대한 정보를 제공합니다.">
    	<colgroup>
   		<col width="40%">
   		<col width="*">
   		</colgroup>
   	<thead>
    <tr>
    	<th scope="col">${salesHeader }</th>
    	<th scope="col">매출액</th>
    
    </tr>
    </thead>
    
    <c:if test="${empty list }">
    	<tr class="align_center">
    		<td colspan="2">해당 매출이 없습니다.</td>
    	</tr>
    </c:if>
    <c:if test="${!empty list }">
    <c:forEach var="map" items="${list }">
		<tr>
			<td class="align_center">${map['ORDER_DATE'] }</td>
			<td class="align_right">
				<fmt:formatNumber value="${map['TOTAL_PRICE'] }" pattern="#,###"></fmt:formatNumber>
			</td>
		</tr>
	</c:forEach>
    </c:if>
    </table>
    </div>
    <%@include file="../../inc/adminBottom.jsp" %>

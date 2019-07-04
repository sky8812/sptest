<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/adminTop.jsp" %>
<style type="text/css">
	caption{
		visibility:hidden;
		font-size:0.1em;
	}	
	.divList, .divRight, .divPage, .divList table{
		width:700px;
	}	
	.divList{
		margin:10px 0;
	}		
	.divPage{
		text-align:center;		
		padding:5px 0;
	}		
	.divRight{
		text-align:right;	
		width:700px;
	}

</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('input[name=chkAll]').click(function(){
			$('input[type=checkbox]').prop('checked',this.checked);
		});
		
		$('#btDelMulti').click(function(){
			
			if($('input[type=checkbox]:checked').length<1){
					alert('삭제할 상품을 먼저 선택하세요');
					return;
				}
					
			$('form[name=frmList]').prop('action','<c:url value="/admin/product/deleteMulti.do"/>');
			$('form[name=frmList]').submit();
					
		});
			
		
		
		$('#btEventMulti').click(function(){

			if($('input[type=checkbox]:checked').length<1){
				alert('삭제할 상품을 먼저 선택하세요');
				return;
			}else if($('select[name=eventSel]:selected').length<1){
					alert('이벤트 등록할 상품을 먼저 선택하세요');
					event.preventDefault();
				}else{
				$('form[name=frmList]').prop('action','<c:url value="/admin/product/eventMulti.do"/>');
				$('form[name=frmList]').submit();
				}
		});
	});

	function boardList(curPage){
		$('input[name=currentPage]').val(curPage);
		$('form[name=frmPage]').submit();
	}
</script>
<h2>상품 목록</h2>
<c:if test="${!empty eventProductVO.eventName }">
	${param.eventName } 상품 ${pagingInfo.totalRecord }건 입니다.
</c:if>
<c:if test="${!empty eventProductVO.eventName }">
	전체 상품 ${pagingInfo.totalRecord }건 입니다.
</c:if>

<!-- 페이징 처리를 위한 form 시작-->
<form name="frmPage" method="post" action="<c:url value='/admin/product/productList.do'/>">
	<input type="text" name="eventName" 	value="${param.eventName }">
	<input type="text" name="currentPage">	
</form>
<!-- 페이징 처리 form 끝 -->

<form name="frmList" method="post" >
<div class="divRight">	
	이벤트 상품 조회
	<select name="eventName">
		<option value=""></option>
		<option value="NEW" 
			<c:if test="${param.eventName=='NEW' }">
				selected="selected"
			</c:if>
		>신상품</option>
		<option value="BEST" 
		<c:if test="${param.eventName=='BEST' }">
				selected="selected"
			</c:if>
		>인기상품</option>
		<option value="MD"
		<c:if test="${param.eventName=='MD' }">
				selected="selected"
			</c:if>
		>MD추천상품</option>				
	</select>
	<input type ="image" src="<c:url value='/resources/images/bt_search3.png'/>" 
		align="absmiddle">		
</div>
<div class="divList">
<table width="700" class="box2" 
	summary="상품목록에 관한 표로써, 번호, 제목, 작성자, 작성일, 조회수에 대한 정보를 제공합니다.">
	<caption>상품 목록</caption>
	<colgroup>
		<col style="width:5%" />
		<col style="width:15%" />
		<col style="width:32%" />
		<col style="width:15%" />
		<col style="width:15%" />	
		<col style="width:9%" />
		<col style="width:9%" />			
	</colgroup>
	<thead>
	  <tr>
		<th><input type="checkbox" name="chkAll"></th>
		<th scope="col">상품이미지</th>
		<th scope="col">상품이름</th>
		<th scope="col">가격</th>
		<th scope="col">등록일</th>
		<th scope="col">수정</th>
		<th scope="col">삭제</th>
	  </tr>
	</thead> 
	<tbody>  
	<c:if test="${empty list }">
		<tr class="align_center">
			<td colspan="7">해당 상품이 없습니다.</td>
		</tr>
	</c:if>
	<!-- 반복 시작 -->
	<c:set var="idx" value="0"/>
		<c:forEach var="vo" items="${list }">
			<tr>
				<td>
				<input type="checkbox" name="pdItems[${idx }].productNo" value="${vo.productNo }">
				<input type="hidden" name="pdItems[${idx }].imageURL" value="${vo.imageURL }">	
				</td>
				<td><img src="<c:url value='/pd_images/${vo.imageURL }'/>" alt="${vo.productName }" width="50px"></td>
				<td>${vo.productName }</td>
				<td><fmt:formatNumber pattern="#,###" value="${vo.sellPrice }"/> </td>
				<td><fmt:formatDate value="${vo.regDate }" pattern="yyyy-MM-dd"/> </td>
				<td><a href="#">수정</a></td>
				<td><a href="#">삭제</a></td>
			</tr>
			<c:set var="idx" value="${idx+1 }"/>
		</c:forEach>
	<!-- 반복 끝 -->
	</tbody>
</table>
</div>
<div class="divPage">
	<!-- 페이지 번호 추가 -->		
	<c:if test="${pagingInfo.firstPage>1 }">
		<a href="#" onclick="boardList(${pagingInfo.firstPage-1})">			
		    <img src='<c:url value="/images/first.JPG" />'  border="0">	</a>
	</c:if>
					
	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	<c:forEach var="i" begin="${pagingInfo.firstPage }" end="${pagingInfo.lastPage }">
		<c:if test="${i==pagingInfo.currentPage }">
			<span style="color:blue;font-weight:bold">${i }</span>
		</c:if>
		<c:if test="${i!=pagingInfo.currentPage }">						
			<a href="#" onclick="boardList(${i})">
				[${i }]
			</a>
		</c:if>		
	</c:forEach>
	
	<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">
		<a href="#" onclick="boardList(${pagingInfo.lastPage+1})">			
			<img src="<c:url value="/images/last.JPG" />" border="0">
		</a>
	</c:if>
	<!--  페이지 번호 끝 -->
</div>

<div class="divRight">
	<input type="button" value="선택한 상품 삭제" id="btDelMulti"><br><br>
	선택한 상품을
	<select name="eventSel">
		<option value=""></option>
		<option value="NEW">신상품으로</option>
		<option value="BEST">인기상품으로</option>
		<option value="MD">MD추천상품으로</option>
	</select>
	<input type="button" value="등록" id="btEventMulti">
</div>
</form>



<%@ include file="../../inc/adminBottom.jsp" %>








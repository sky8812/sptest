<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>zipcode.jsp</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/mainstyle.css'/>">
<style type="text/css">
	.divTbl{
		width:500px;
		margin-top:20px;
	}
	
	.divTble .box2{
		width:490px;
	}
	
	form[name=frmZip] label{
		font-size: 0.9em;
	}
	
	h1{
		font-size: 1.5em;
		margin-bottom:25px;
	}
	.error{
		color: red;
		display: none; 
	}
	.divPage{
		text-align: center;
		margin-top:10px;
	}
</style>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$('#dong').focus();
		
		$('form[name=frmZip]').submit(function(){
			if($('#dong').val().length<1){
				$('.error').show();
				event.preventDefault();
				$('#dong').focus();				
			}
		});
	});
	
	 function setZipcode(zipcode, address){
		  /* opener.document.frm1.zipcode.value=zipcode;
		  opener.document.frm1.address.value=address; */
		  $(opener.document).find("#zipcode").val(zipcode);
		  $(opener.document).find("input[name=address]").val(address);
		  self.close();
	  }
	/*  function pageFunc(curPage){
		 document.frmPage.currentPage.value=curPage; //선택한 page번호를 currentPage값에 넣기
		 document.frmPage.submit(); //submit은 함수, 보라색=속성(바꾸려면->frmPage.name="바꿀내용")
	 } */
	 
	 function pageFunc(currPage){
		 $('input[name=currentPage]').val(currPage);
	 	 $('form[name=frmZip]').submit();
	 }
</script>
</head>
<body>

<h1>우편번호 검색</h1>
<p>찾고 싶으신 주소의 동(읍,면)을 입력하세요</p>
<form name="frmZip" method="get" action="<c:url value='/zipcode/zipcode.do'/>">
	<input type="text" name="currentPage" value="1">
	<label for="dong">지역명</label>
	<input type="text" name="dong" id="dong" value="${param.dong }">
	<input type="submit" value="찾기">
	<span class="error">지역명을 입력하세요.</span>
	
</form>

<c:if test="${list !=null }">
<div class="divTbl">
<table class="box2" summary="우편번호 검색 결과에 관한 표로써 우편번호, 주소에 대한 정보를 제공합니다.">
	<colgroup>
		<col style="width:20%">
		<col style="width:*">
	</colgroup>
		<thead>
			<tr>
				<th scope="col">우편번호</th>
				<th scope="col">주소</th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${empty list }">
				<tr>
					<td colspan="2" class="align_center">해당 주소가 존재하지 않습니다.</td>
				</tr>
		</c:if>
			<!-- 반복 시작 -->
			<c:if test="${!empty list }">
				<c:forEach var="vo" items="${list }">
				<c:set var="address" value="${vo.sido } ${vo.gugun } ${vo.dong }"/>
				<c:set var="bunji" value="${vo.startbunji }"/>
				<c:if test="${!empty vo.endbunji }">
				<c:set var="bunji" value="${bunji } ~ ${vo.endbunji }"/>
				</c:if>
				<tr>
					<td>${vo.zipcode }</td>
					<td><a href="#" onclick="setZipcode('${vo.zipcode }','${address } ${bunji }')">
					${address } ${bunji }</a></td>
				</tr>
				</c:forEach>
			<%-- <%
			int num=pagevo.getNum();
			int curpos=pagevo.getCurPos();
			
			for(int i=0;i<pagevo.getPageSize();i++){ 
				if(num-- <1) break;
				
				ZipcodeVO vo=list.get(curpos++);
			
				String address=vo.getSido()+" "+vo.getGugun()+" "+vo.getDong();
				
				String bunji=vo.getStartbunji();
				if(vo.getEndbunji()!=null && !vo.getEndbunji().isEmpty()){
					bunji+=" ~ "+vo.getEndbunji();
				}
				
				%>
				<tr>
					<td><%=vo.getZipcode() %></td>
					<td><a href="#" onclick="setZipcode('<%=vo.getZipcode() %>','<%=address %>')">
					<%=address+bunji %></a></td>
				</tr>
				
			<%} 
		}//else %> --%>
		</c:if>
		</tbody>
</table>
</div>
<div class="divPage">
	<!-- 이전블럭으로 이동하기 -->
	<c:if test="${pagingInfo.firstPage>1 }">	
		<a href="#" onclick="pageFunc(${pagingInfo.firstPage-1})">
			<img src="<c:url value='/resources/images/first.JPG'/>" alt="이전블럭으로 이동">
		</a>	
	</c:if>
	<!-- 페이지 번호 추가 -->
	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	<c:forEach var="i" begin="${pagingInfo.firstPage }" end="${pagingInfo.lastPage }">
			<c:if test="${i==pagingInfo.currentPage }">
				<span style="color:blue;font-size: 1em">${i }</span>
			</c:if>
			<c:if test="${i!=pagingInfo.currentPage }">
				<a href="#" onclick="pageFunc(${i})">
					[${i}]</a>
			</c:if>
	</c:forEach>
	<!--  페이지 번호 끝 -->
	
	<!-- 다음 블럭으로 이동하기 -->
	<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">	
		<a href="#" onclick="pageFunc(${pagingInfo.lastPage+1})">
			<img src="<c:url value='/resources/images/last.JPG'/>" alt="다음블럭으로 이동">
		</a>
	</c:if>
</div>

</c:if>

	<!-- 이전블럭으로 이동하기 -->
	<%-- <%if(pagevo.getFirstPage()>1){ %>
		-- <a href="zipcode.jsp?currentPage=<%=pagevo.getFirstPage()-1 %>&dong=<%=dong %>">--
		<a href="#" onclick="pageFunc(<%=pagevo.getFirstPage()-1%>)"><img src="../images/first.JPG" alt="이전블럭으로 이동">
			<img src="../images/first.JPG" alt="이전블럭으로 이동">
		</a> 
	<%}//if %> --%>
	
	<!-- 페이지 번호 추가 -->
	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	<%-- <%
		for(int i=pagevo.getFirstPage();i<=pagevo.getLastPage();i++){
			if(i> pagevo.getTotalPage()) break;	
			
			if(i==currentPage){%>
				<span style="color:blue;font-size: 1em"><%=i %></span>
			<%}else{ %>
				 <a href="zipcode.jsp?currentPage=<%=i%>&dong=<%=dong %>">
					[<%=i %>]</a> 
					<a href="#" onclick="pageFunc(<%=i %>)">[<%=i %>]</a><!--(post방식으로 처리) url이 아닌 javascript로 처리하기  -->
					<!-- 현재 페이지 i 매개변수로 보내기 -->
			<%}//if %>
	<%	}//for	%> --%>
	<!--  페이지 번호 끝 -->
	
	<!-- 다음 블럭으로 이동하기 -->
	<%-- <%if(pagevo.getLastPage()<pagevo.getTotalPage()){ %>
		-- <a href="zipcode.jsp?currentPage=<%=pagevo.getLastPage()+1%>&dong=<%=dong %>"> 
		<a href="#" onclick="pageFunc(<%=pagevo.getLastPage()+1%>)">
			<img src="../images/last.JPG" alt="다음블럭으로 이동">
		</a>
	<%} %>		 --%>
</body>
</html>
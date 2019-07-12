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
	
	.box2{
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
	
	.sample{
		color:steelblue;
	}
	#divZip, #divCount{
		margin: 10px 0;
	}
	#divPage{
		text-align: center;
		margin:5px 0;
	}
</style>
<script type="text/javascript" src="<c:url value='/resources/js/paging.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$('#dong').focus();
		
		$('form[name=frmZip]').submit(function(){
				event.preventDefault();
			if($('#dong').val().length<1){
				$('.error').show();
				$('#dong').focus();		
				return ;
			}
				$('.error').hide();
				
				$.send(1); //default로 1페이지 조회
			
		}); //submit
		
		$.send=function(curPage){
			//현재 페이지 setting
			$('input[name=currentPage]').val(curPage);
			
			$.ajax({
				url:"<c:url value='/sample/getAddrApi.do'/>",
				type:"post",
				data: $('form[name=frmZip]').serialize(),
				dataType:"xml",
				success:function(res){
					var errorCode=$(res).find("errorCode").text();
					var errorMsg=$(res).find("errorMessage").text();
					
					//alert(errorCode);
					if(errorCode!="0"){ //error
						alert(errorCode+"-"+errorMsg);
					}else{
						if(res!=null){
							makeList(res);
						}
					}
				},
				error:function(xhr,status, error){
					alert(status+":"+error);
				}
			});	
		}
		
	}); //document.ready
	
	function makeList(xmlStr){
		$('#divCount').html("");
		$('#divZip').html("");
		$('#divPage').html("");
		//alert(xmlStr);
		var totalCount=$(xmlStr).find("totalCount").text();
		var tableEl=$("<table class='box2'></table>").html('<tr><th>우편번호</th><th>주소</th></tr>');
		if(totalCount==0){
			var trEl1=$('<tr></tr>').html("<td>해당 주소가 없습니다.</td>");
			tableEl.append(trEl1);
			$('#divZip').html(tableEl);
			return;
		}
		$('#divCount').html("<p>도로명 주소 검색 결과 ("+totalCount+"건)</p>")
		
		$(xmlStr).find('juso').each(function(idx,item){
			var roadAddress=$(this).find("roadAddr").text();
			var zipNo=$(this).find("zipNo").text();
			
			var trEl=$('<tr></tr>');
			var tdEl1=$('<td></td>').html(zipNo);
			var anchor=$('<a href="#"></a>').html(roadAddress).attr('onClick',"setZipcode('"+zipNo+","+roadAddress+"')");
			var tdEl2=$("<td></td>").html(anchor);
			
			trEl.append(tdEl1);
			trEl.append(tdEl2);
			tableEl.append(trEl);
		});
		
		$('#divZip').html(tableEl);
		
		//페이징 처리
		var p_recordCountPerPage=10;
		var p_blockSize=10;
		var p_curPage=$('input[name=currentPage]').val();
		
		pagination(p_curPage,p_recordCountPerPage,p_blockSize,totalCount);
		
		$.pageSetting();
	}
	
	$.pageSetting=function(){
		//<!-- 이전블럭으로 이동하기 -->
		if(firstPage>1){
			var anchor=$('<a href="#"></a>').html("<img src='<c:url value='/resources/images/first.JPG'/>' alt='이전블럭으로 이동'>")
			.attr("onclick","$.send("+(firstPage-1)+")");
		
			$('#divPage').html(anchor);
		}
	//	<!-- 페이지 번호 추가 -->
	//	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	for(i=firstPage;i<lastPage;i++){
		if(i==currentPage){
			var spanEl=$("<span style='color:blue;font-size:1em'></span>").html(i);
			$('#divPage').append(spanEl);
		}else{
			var anchor=$("<a href='#'></a>").html("["+i+"]").attr("onclick","$.send("+i+")");
			$('#divPage').append(anchor);
		}
	}
	
		
		//<!-- 다음 블럭으로 이동하기 -->
		if(lastPage<totalPage){
			var anchor=$("<a href='#'></a>").html("<img src='<c:url value='/resources/images/last.JPG'/>' alt='다음블럭으로 이동'>")
			.attr("onclick","$.send("+(lastPage+1)+")");
			$('#divPage').append(anchor);
		}

		
	}
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
	 
	
</script>
</head>
<body>

<h1>도로명주소 검색</h1>
<p>도로명주소, 건물명 또는 지번을 입력하세요.</p>
<p class="sample">검색어 예 : 건물명(반포대로 58), 건물명(독립기념관), 지번(삼성동25)</p>
<form name="frmZip" method="post">
  <input type="text" name="currentPage" value="1"/>				<!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
  <input type="hidden" name="countPerPage" value="10"/>				<!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
  <input type="hidden" name="confmKey" value="U01TX0FVVEgyMDE3MTIxODE3Mzc0MTEwNzU1Njg="/>		<!-- 요청 변수 설정 (승인키) -->

	<label for="dong">지역명</label>
	<input type="text" name="keyword" id="dong" value="${param.dong }">
	<input type="submit" value="찾기">
	<span class="error">지역명을 입력하세요.</span>
	
</form>


<div id="divCount"></div>
<div id="divZip"></div>
<div id="divPage"></div>



<div class="divPage">
	
</div>


</body>
</html>
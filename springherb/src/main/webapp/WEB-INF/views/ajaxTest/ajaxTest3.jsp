<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
<script type="text/javascript">

	$(function(){
		$('#query').click(function(){
			var no=$("#no").val();
			
			//{"no":10,"name":"홍길동","content":"내용"}
			$.ajax({
				url:"<c:url value='/ajaxTest/view2.do'/>",
				type:"get",
				data:"no="+no, //하나일 때는 객체{}로 안해도 됨
				dataType:"json",
				success:function(res){
					var str=res.no;
					//$('#no').val(str);
					//alert(res);
					$('#result').html(str);
				},
				error:function(xhr,status,error){
					alert(status+":"+error);
				}
				
			});
		});
		
		//{"message":"메모 등록 성공","data":{"no":10,"name":"hong","content":"hi"}}
		$('#frm1').submit(function(){
			alert($.param($(this).serializeArray()));
			var name=$('#name').val();
			var content=$('#content').val();
			
			$.ajax({
				url:"<c:url value='/ajaxTest/memoWrite.do'/>",
				type:"post",
				//data:{"name":name, "content":content},
				//data:$(this).serialize(), 	//입력상자의 내용을 쿼리문자열로 변환
				data:$(this).serializeArray(), 	//입력상자의 내용을 객체로
				dataType:"json",
				success:function(res){
					var str=res.message+"<br>";
					str+="번호 : "+res.data.no+"<br>";
					str+="이름 : "+res.data.name+"<br>";
					str+="내용 : "+res.data.content;
			
					$('#result').html(str);
				},
				error:function(xhr,status, error){
					alert(status+":"+error);
				}
			});
			
			event.preventDefault();
		});
		
	});
</script>
</head>
<body>
	<form id="frm1">
		번호 : <input type="text" id="no" size="7" /> <input type="button"
			id="query" value="조회"><br>
		<h2>메모를 남기세요</h2>
		이름 : <input type="text" id="name" name="name" /><br> 메모 : <input
			type="text" id="content" name="content" size="50" /><br> <input
			type="submit" value="입력">
		<h2>결과</h2>
		<div id="result" style="background: #eeeeee; width: 500px"></div>
	</form>
</body>
</html>
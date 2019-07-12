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
		//{"no":1,"name":"홍길동","content":"안녕"} =>이 전체(객체 하나)가 res
		$('button').first().click(function(){
			$.ajax({
				url:"<c:url value='/ajaxTest/view.do'/>",
				type:"post",
				dataType:"json",
				success:function(res){
					//alert(res);
					var str=" 번호: "+res.no+", 이름: "+res.name+", 내용: "+res.content;
					$('#info').html(str);
				},
				error:function(xhr, status, error){
					alert(status+": "+error);
				}
			});
		});
		
		//[{"no":1,"name":"김길동","content":"안녕하세요 "},{"no":2,"name":"이길동","content":"안녕 "},{"no":3,"name":"한길동","content":"하이 "}]
		//[]대괄호가 하나의 res
		$('button').eq(1).click(function(){
			$.ajax({
				url:"<c:url value='/ajaxTest/list.do'/>",
				type:"get",
				dataType:"json", 
				success:function(res){
					//alert(res.length);
					if(res.length>0){
						var str='<hr>';
						$.each(res,function(idx, item){
							str+="<p>번호: "+item.no+", 이름: "+item.name+", 내용:"+this.content+"</p>";
						});
						$('#info').append(str);
					}
				},
				error:function(xhr,status, error){
					alert(status+":"+error);
				}
			});
		});
		
		$('button').eq(2).click(function(){
			$.getJSON("<c:url value='/ajaxTest/view.do'/>",function(res){
				str=res.no+","+res.name+","+res.content;
				$('#info').html(str);
			});
		});
		
		$('button').eq(3).click(function(){
			$('#info').load("<c:url value='/ajaxTest/search.do'/>");
		});
		
		$('button').eq(4).click(function(){
			$.get("<c:url value='/ajaxTest/search.do'/>","id=hong&keyword=s",function(res){
				$('#info').html(res);
			});
		});
	});
</script>
</head>
<body>
	<h1>ajax 연습2</h1>
	<button>VO리턴</button>
	<button>List리턴</button>
	<button>getJson()리턴</button>
	<button>load()리턴</button>
	<button>get()리턴</button>
	<br><br>
	<div id="info"></div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.min.css'/>" >
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>

<script type="text/javascript">

$( function() {
	$.setToday();
	
	$('#btWeek').click(function(){
		$.setDate('d',7); //1주일전	
	});
	
	$('#btMonth1').click(function(){
		$.setDate('m',1); //1개월전	
	});
	
	$('#btMonth3').click(function(){
		$.setDate('m',3); //3개월전	
	});
	
	 	$( "#startDay" ).datepicker({
	 		
	 	dateFormat: "yy-mm-dd",
	 	changeYear: true,
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ]
	 	});
	 	
	$( "#endDay" ).datepicker({
		dateFormat: "yy-mm-dd",
   	 	changeYear: true,
   		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ]
	});

} );

	$.setDate=function(type,term){
		var endDate=$('#endDay').val();
		var arr=endDate.split('-');
		var date=new Date(arr[0],arr[1]-1,arr[2]);
		
		if(type=='d'){
			date.setDate(date.getDate()-term);	
		}else if(type=='m'){
			date.setMonth(date.getMonth()-term);
		}
		
		var str=$.findDate(date);
		$('#startDay').val(str);
	}
	
	
$.setToday=function(){
	if($('#startDay').val()==''){
		var d= new Date();
		var str=$.findDate(d);
		$('#startDay').val(str);
		$('#endDay').val(str);
	}
}

$.findDate=function(d){
	var month=d.getMonth()+1;
	var date=d.getDate();
	var str=d.getFullYear()+"-"+$.formatDate(month)+"-"+$.formatDate(date);
	return str;
}

$.formatDate=function(month){
	if(month<10){
		return "0"+month;
	}else{
		return month;
		
	}
}

</script>
조회기간
	<input type="button" value="1주일" id="btWeek">
	<input type="button" value="1개월" id="btMonth1">
	<input type="button" value="3개월" id="btMonth3">
		
		 <!-- 매개변수에 있는 modelattribute 읽어갈 수 있음(앞글자 소문자로 바꿔서) -->
	<input type="text" name="startDay" id="startDay" value="${param.startDay }"> 
	~ 
	<input type="text" name="endDay" id="endDay" value="${dateSearchVO.endDay }">

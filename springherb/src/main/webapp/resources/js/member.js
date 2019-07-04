/**
 * member.js 클라이언트영역이라 서버X, c태그는 jsp만가능
 */
var contextPath="/herb";
$(function(){
		$('select[name=email2]').change(function(){
			if($(this).val()=='etc'){
				$('input[name=email3]').css('visibility','visible');
				$('input[name=email3]').focus();
				$('input[name=email3]').val('');
			}else{
				$('input[name=email3]').css('visibility','hidden');
				
			}
		});
		
		
		$('#btnZipcode').click(function(){
			window.open(contextPath+"/zipcode/zipcode.do", "zip",
			"top=0,left=0,width=500,height=400,location=yes,resizable=yes");
		});
		
		$('#btnChkId').click(function(){
			var userid=$('#userid').val();
			window.open(contextPath+"/member/checkUserid.do?userid="+userid, "chk", "top=10,left=10,width=400,height=200,location=yes,resizable=yes")
			
		});

	}); //
	
	function validate_userid(userid){
		var pattern=new RegExp(/^[a-zA-Z0-9_]+$/g);
		return pattern.test(userid);
	}
	
	function validate_hp(hp){
		var pattern=new RegExp(/^[0-9]*$/g);
		return pattern.test(hp);
		/* 정규식  /^[0-9]*$/g
			0에서9사이의 숫자로 시작하거나 끝나야 한다는 의미
			(^는 시작, $는 끝을 의미)
		닫기 대괄호(])뒤의 * 기호는 0번이상반복
		*/
	}
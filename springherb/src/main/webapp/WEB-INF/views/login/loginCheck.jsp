<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<%
	String l_userid=(String)session.getAttribute("userid");  /* include할 memberEdit의 userid와 충돌나면 안되므로 이름을 다르게 */
	if(l_userid==null || l_userid.isEmpty()){%>
		<script type="text/javascript">
		alert('먼저 로그인하세요');
		location.href="<%=request.getContextPath() %>/login/login.jsp"; //절대참조로 해야함
		</script>
		
	<% return; /* servlet에서 서비스 메서드로 들어가는 것이므로 return */
	}
%>

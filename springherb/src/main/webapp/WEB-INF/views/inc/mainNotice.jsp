<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
	#divNotice {
		width:300px;
	}
	
	#divNotice .more{
		padding: 0 0 0 50%;
	}
	#divNotice div.line img{
		width: 300px;
		height: 5px;
	}
	/* #divNotice div.line{
		margin-top: 6px;
	} */
	#divNotice div.tbl table{
		width: 290px;
	}
</style>
<div id="divNotice">
	<div>
		<img src="${pageContext.request.contextPath }/resources/images/notice2.JPG"
			alt="공지사항 이미지"> <a href="<c:url value='/board/list.do'/>" > <img
			src="${pageContext.request.contextPath }/resources/images/more.JPG" alt="more이미지">
		</a>
	</div>
	<div>
		<img src="${pageContext.request.contextPath }/resources/images/Line.JPG">
	</div>
	<div class="tbl">
		<table>
		<c:if test="${empty list }">
		<tr>
				<td>공지사항이 없습니다.</td>
			</tr>
		</c:if>
		<c:if test="${!empty list }">
			<c:forEach var="vo" items="${list }">
				<tr>
				<td><img src="${pageContext.request.contextPath }/resources/images/dot.JPG">
				<a href="<c:url value='/board/detail.do?no=${vo.no}'/>" >
				<c:if test="${fn:length(vo.title)>30 }">
					${fn:substring(vo.title,0,30) }...
				</c:if>
				<c:if test="${fn:length(vo.title)<30 }">
					${vo.title }
				</c:if></a>
				</td>
			</tr>
			</c:forEach>
		</c:if>
		
		<%-- <%if(list==null || list.isEmpty()){
			%>
			<tr>
				<td>공지사항이 없습니다.</td>
			</tr>
		<%}else{
			for(int i=0;i<list.size();i++){
				BoardVO vo=list.get(i);
			//for(BoardVO vo:list){ ->확장 for
			%> -
			<tr>
				<td><img src="<%=request.getContextPath() %>/images/dot.JPG">
				<a href="<%=request.getContextPath() %>/board/detail.jsp?no=<%=vo.getNo() %>"><%=vo.getTitle() %></a>
				</td>
			</tr>
			<%}
			} --%>
			
		</table>
	</div>
</div>

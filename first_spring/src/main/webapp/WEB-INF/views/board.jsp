<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">

*{
	margin: 0;
	padding: 0;
}

#main{
	text-align: center;
}

table{
	width: 800px;
	height: 500px;
	border-collapse: collapse;
	margin: 0 auto;
}
td{
	background-color: white;
}
tr{
	border: 1px gray solid;
}

#paging{text-align: center; margin: 10px auto;}
#paging img{vertical-align: middle;}
#paging a{display:inline-block; padding: 0 3px; text-decoration: none; color: black;}
#paging a:hover{background-color:#EAFAF1; border-color:#eee; box-shadow:3px 3px;}

</style>
<script type="text/javascript">
function linkPage(pageNo){
	location.href="./board?pageNo="+pageNo;//아직 미완성입니다. sb_cate도...
	
}
</script>

</head>
<body>
	<div id="container">
		
		<div id="header">
			<!--<jsp:include page="menu.jsp"/>-->
			<c:import url="/menu"/>
		</div>
		
		<div id="main">

		<h1>${category }</h1>
		<h2>
		<c:choose>
			<c:when test="${sessionScope.sm_name ne null }">
				세션 : ${sessionScope.sm_name }님 반갑습니다. 
				<a href="./logout">로그아웃</a>
			</c:when>
			<c:otherwise>
				<a href="./">로그인</a>
			</c:otherwise>
		</c:choose>
		
		</h2>
		<!-- 
		<h1>
			<c:if test="${param.sb_cate eq 4}">자유게시판</c:if>
			<c:if test="${param.sb_cate eq 5}">공지사항</c:if>
			<c:if test="${param.sb_cate eq 6}">문의게시판</c:if>
		</h1>
		 -->
		
	<!--  전체 리스트 찍기 : ${list } -->
		<c:choose>
			<c:when test="${fn:length(list) gt 0 }">
				<table>
					<tr>
						<td>번호</td>
						<td>제목</td>
						<td>날짜</td>
						<td>조회수</td>
						<td>글쓴이번호</td>
					</tr>
					<c:forEach items="${list }" var="l">
						<tr>
							<td id="no">${l.sb_nno }</td>
							<td>
							<a href="./detail?sb_no=${l.sb_no }">${l.sb_title }</a>
							</td>
							<td>${l.sb_date }</td>
							<td>${l.sb_count }</td>
							<td>${l.sm_name }(${l.sm_id })</td>
						</tr>
					
					</c:forEach>
				</table>
				<!-- 페이징은 여기에 -->
				<div id="paging">
					<ui:pagination paginationInfo="${paginationInfo }" type="image" jsFunction="linkPage"/>
				</div>
			</c:when>
			<c:otherwise>
				출력할 글이 없습니다.
			</c:otherwise>
		</c:choose>
		
		<!-- 로그인 한 사용자에게만 글쓰기 버튼 보이게 -->
		<c:if test="${sessionScope.sm_name ne null }">
			<a href="./write?sb_cate=${sb_cate }">글쓰기</a>	
		</c:if>
		
		</div>
	</div>
	
</body>
</html>


<!-- 
java 11.0.12
tomcat 9.0.52
spring 3.9.17 
 -->
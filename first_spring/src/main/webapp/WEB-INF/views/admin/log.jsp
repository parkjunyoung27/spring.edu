<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>log기록</title>
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
	location.href= "./log?pageNo="+pageNo+
			"<c:if test="${search ne null}">&searchName=${searchName}&search=${search}</c:if>";
}
</script>

</head>
<body>
	<div id="container">
		
		<div id="header">
			<a href='./category'>카테고리</a>
			<a href='./member'>회원관리</a>
			<a href='./log'>사용기록</a>	
		</div>
		<h1>로그리스트 ${totalCount }개의 글이 있습니다.</h1>
		<c:if test="${searchName ne null}">
			<h2>검색조건:${searchName} / 검색어:${search }</h2>
			<button onclick="location.href='./log'">검색초기화</button>
		</c:if>
	<!--  전체 리스트 찍기 : ${loglist } -->
		<c:choose>
			<c:when test="${fn:length(list) gt 0 }">
				<table>
					<tr>
						<td>번호</td>
						<td>ip</td>
						<td>날짜</td>
						<td>target</td>
						<td>id</td>
						<td>data</td>
					</tr>
					<c:forEach items="${list }" var="l">
						<tr>
							<td id="lno">${l.sl_no }</td>
							<td>
							<a>${l.sl_ip }</a>
							</td>
							<td>${l.sl_date }</td>
							<td>${l.sl_target }</td>
							<td>${l.sl_id }</td>
							<td>${l.sl_data }</td>
						</tr>
					
					</c:forEach>
				</table>
				<!-- 페이징은 여기에 -->
				<div id="paging">
					<ui:pagination paginationInfo="${paginationInfo }" type="image_admin" jsFunction="linkPage"/>
				
					<form action="./log" method="get">
						<select name="searchName">
							<option value="ip" <c:if test="${searchName eq 'ip' }">selected="selected"</c:if>>ip</option>
							<option value="id" <c:if test="${searchName eq 'id' }">selected="selected"</c:if>>id</option>
							<option value="data" <c:if test="${searchName eq 'data' }">selected="selected"</c:if>>내역</option>
							<option value="date" <c:if test="${searchName eq 'date' }">selected="selected"</c:if>>날짜</option>
						</select>
						<input type="text" name="search" value="${search }"> <!-- name값 잡아야지 쓸 수 있음 -->
						<button>검색</button>
					</form>		
					
				</div>
			</c:when>
			<c:otherwise>
				출력할 글이 없습니다.
			</c:otherwise>
		</c:choose>
	
	</div>
</body>
</html>

















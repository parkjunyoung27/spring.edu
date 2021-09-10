<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>category</title>
<style type="text/css">
	table {
		margin:0 auto;
		width: 500px;
		border-collapse: collapse;;
}
tr:nth-child(even){
	background-color: #d2d2d2;
}
tr:hover{
	background-color: #c0c0c0;
}
td{
	border-bottom: 1px gray solid; 
}
</style>
</head>
<body>
	<div id="header">
		<a href='./category'>카테고리</a>
		<a href='./member'>회원관리</a>
		<a href='./log'>사용기록</a>	
	</div>
	<table>
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>날짜</th>
			<th>수정</th>
			<th>노출여부</th>
		</tr>
		<c:forEach items="${category }" var="c">
			<tr>
				<td>${c.sc_no }</td>
				<td>${c.sc_category }</td>
				<td>${c.sc_date }</td>
				<td>
					<a href="./categoryUpdate?sc_no=${c.sc_no }">[수정]</a>
				</td>
				<td>
					<a href="./categoryVisible?sc_no=${c.sc_no }">
						<c:choose>
							<c:when test="${c.sc_visible eq 1 }">
								<img alt="" src="../images/on.png"
									style="vertical-align: middle;">[공개]
							</c:when>
							<c:otherwise>
								<img alt="" src="../images/off.png"
									style="vertical-align: middle;">[비공개]
							</c:otherwise>
						</c:choose>
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<!-- 이 버튼을 누르면 비공개로 변경하기 
		-> 데이터베이스에 컬럼 -->

	<!-- 	//추가하는 폼 -->
	<form action="./category" method="post">
		<input type="text" name="categoryName">
		<button type="submit">등록</button>
	</form>
	<hr>

<!-- admin은 공개를 안하기때문에 url 직접 입력해 들어가야함 -->
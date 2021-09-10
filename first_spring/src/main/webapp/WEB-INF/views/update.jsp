<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정하기</title>
<link href="./css/index.css" rel="stylesheet">
</head>
<body>
	<div id="container">
	<div id="header">
		<!--<jsp:include page="menu.jsp"/>-->
		<c:import url="/menu"/>
	</div>
	
	<form action="update" method="post">
		<input type="text" name="sb_title" value="${dto.sb_title }">
		<textarea name="sb_content">${dto.sb_content }</textarea>
		<input type="hidden" name="sb_no" value="${dto.sb_no }">
		<button>글쓰기</button>
	</form> 
	</div>
</body>
</html>
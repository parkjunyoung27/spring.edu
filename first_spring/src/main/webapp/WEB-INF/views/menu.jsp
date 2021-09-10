<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="loginform" style="color: white;">
	<c:choose>
		<c:when test="${sessionScope.sm_name ne null }">
			세션 : ${sessionScope.sm_name }님 반갑습니다. 
			<a href="./logout" style="color:white;">로그아웃</a>
		</c:when>
		<c:otherwise>
			<form action="./" method="post">
				<input type="text" name="id" required="required">
				<input type="text" name="pw" required="required">
				<button type="submit">로그인</button>
				<button type="reset">초기화</button>
			</form>
		</c:otherwise>
	</c:choose>
</div>

<div id="menu">
	<c:forEach items="${categoryList }" var="cl">
		<a href="./board?sb_cate=${cl.sc_no }">${cl.sc_category } | </a>
	</c:forEach>
</div>
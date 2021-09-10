<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member</title>
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
	text-align: center;
}
tr{
	border: 1px gray solid;
}

</style>

<script type="text/javascript">
function down(no, grade){
	//alert("down" + no);
	var grade = parseInt(grade) - 1;
	location.href="./gradeDown?sm_no="+no+"&grade="+grade;
}

function up(no, grade){
	//alert("up" + no);
	var grade = parseInt(grade) + 1;
	location.href="./gradeUp?sm_no="+no+"&grade="+grade;	
}

function userDelete(no){
	if(confirm("삭제할까요?")){
		alert(no+"번 회원을 삭제합니다.");
		location.href="./userDelete?sm_no="+no;
	}
}

</script>

</head>
<body>
	<div id="header">
		<a href='./category'>카테고리</a>
		<a href='./member'>회원관리</a>
		<a href='./log'>사용기록</a>	
	</div>
		
	<h1>전체 사용자 리스트</h1>
	
		<!--  전체 리스트 찍기 : ${list } -->
		<c:choose>
			<c:when test="${fn:length(list) gt 0 }">
				<table>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>가입일</th>
						<th>등급</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${list }" var="l">
					<tr <c:if test="${l.sm_grade lt 5}">
							style="background-color: gray"</c:if> >
						<td>${l.sm_id }</td>
						<td>${l.sm_name }</td>
						<td>${l.sm_joindate }</td>
						<td>
							<button <c:if test="${l.sm_grade eq 1 }">disabled="disabled"</c:if> onclick="down(${l.sm_no},${l.sm_grade})">◀</button>	
							${l.sm_grade }
							<button <c:if test="${l.sm_grade eq 9 }">disabled="disabled"</c:if> onclick="up(${l.sm_no}, ${l.sm_grade})">▶</button>	
						</td>
						<td><img alt="delete" src="../images/delete.png" width="24px" onclick="userDelete(${l.sm_no})"></td>
																									<!-- id로 넣을꺼면 ""넣어야됨 -->
					</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				출력할 글이 없습니다.
			</c:otherwise>
		</c:choose>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail : ${dto.sb_no }</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#detail{
	margin: 0 auto;
	width: 800px;
	height: auto;
	padding:5px;
	background-color: white;
	text-align: center;
}

#detail table{
	margin: 0 auto;
}
</style>

<script type="text/javascript">
function like(no){
	var no = no;
	if("${sm_id}" == ""){
		var result = confirm("로그인창으로 이동하시겠습니까?")
		if(result == true){
			location.href="./login";					
		}
	}else{
		var result = confirm("좋아요 누르시겠습니까?")			
		if(result == true){
			location.href="./like?sb_no="+no;
		}
	}
}

</script>
</head>
<body>
	<div id="container">
	<div id="header">
		<!--<jsp:include page="menu.jsp"/>-->
		<c:import url="/menu"/>
	</div>

	<div id="detail">
        <table border="1" style="width:600px">
            <caption>게시판</caption>
            <colgroup>
                <col width='15%' />
                <col width='*%' />
            </colgroup>
            <tbody>
                <tr>
                    <td>글 번호</td>
                    <td><c:out value="${dto.sb_no }"/></td>
                </tr>
                <tr>
                    <td>글 제목</td>
                    <td><c:out value="${dto.sb_title }"/>
                    <c:if test="${sessionScope.sm_id eq dto.sm_id }">
						<button onclick="location.href='./delete?sb_no=${dto.sb_no}'">삭제하기</button> 
						<button onclick="location.href='./update?sb_no=${dto.sb_no}'">수정하기</button> 
                    </c:if>
                    </td>
                </tr>
                <tr>
                    <td>글쓴이</td>
                    <td><c:out value="${dto.sm_name }"/></td>
                </tr>
                <tr>
                    <td>id</td>
                    <td><c:out value="${dto.sm_id }"/></td>
                </tr>
                <tr>
                    <td>날짜</td>
                    <td><c:out value="${dto.sb_date }"/></td>
                </tr>
                <tr>
                    <td>조회수</td>
                    <td><c:out value="${dto.sb_count }"/></td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td>
                    	${dto.sb_content }
                    	${dto.sb_file }
                    	<c:if test="${dto.sb_file != null }">
                    		<img alt="" src="./upfile/${dto.sb_file }" width="700px;">
                    	 </c:if>
                    </td>
                </tr>
                <tr>
                    <td>좋아요</td>
                    <td> ${dto.likecount}
                    <img alt="like" src="./images/like_before.png" onclick="like(${dto.sb_no })">
                    </td>
                </tr>
            </tbody>
        </table>   
	</div>
		
		<a href="./board">게시판</a>
		<a href="./write?sb_cate=${dto.sb_cate }">글쓰기</a>
		
	</div>
</body>
</html>
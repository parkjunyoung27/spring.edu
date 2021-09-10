<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<!-- summer note -->
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

</head>

<body>
	<div id="container">
		<div id="header">
			<c:import url="/menu" />
		</div>
		<div id="main">
			<div id="update">
			
				<form action="write" method="post" enctype="multipart/form-data">
					<input type="text" name="sb_title">
					<textarea name="sb_content" id="summernote"></textarea>
					<input type="file" name="file" accept=".png, .jpg, .gif, .jpeg, .bmp">
					<button>글쓰기</button>
					<input type="hidden" name="sb_cate" value="${param.sb_cate}">
				</form>
			
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function() {
	  $('#summernote').summernote({
		  height:400,
		  
	  });
	});
</script>
</html>
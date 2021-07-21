<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시글 작성</title>
<style>
	#container{width:fit-content; padding:20px; margin:0px auto;}
	#container>h2{text-align:center;}
	#userid{position: absolute; right: 100px;}
	#contents{ overflow: auto; width:700px; height:100px; border:1px solid black; margin-top: 10px;}
	div#title{padding: 5px 5px;}
	input#title{width: 500px;}
	.log_a{position: absolute; left: 70px; text-decoration: none;}
	.log_a:hover {color:gold;}
	.filebtn{ padding: 3px 6px; background-color:#f5f5f5; color:#505050; border-radius: 4px; cursor: pointer;}
	.buttonbox .filetext {display: inline-block;  height: 18px;  font-size:14px;   padding: 3px 6px;  vertical-align: middle;  background-color: #fdfdfd;
	 border: 1px solid #828282;  border-radius: 5px;}
	.buttonbox .upbtn {padding: 3px 6px; background-color:#f5f5f5; color:#505050; border-radius: 4px; cursor: pointer;}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$(function(){
  var fileTarget = $('#files');
  fileTarget.on('change', function(){ // 값이 변경되면
      var cur=$(".buttonbox input[type='file']").val();
    $(".filetext").val(cur);
  });
}); 
</script>
</head>
<body>
<div id='userid'> ${userId} <a href="/mb/bbs/logout" class=log_a>logout</a></div>
<div id="container">
	<form action="/mb/bbs/add/getkey" method="post" id="uploadform" enctype="multipart/form-data">
		<h2>게시글 작성</h2>
		<div id =boardwrite>
		<input type="hidden" name="writer" id="writer" value="${userId}">
		<div id="title"><label>제 목</label><input type="text" name="title" id="title"></div>
		<textarea rows="30" cols="40" name="contents" id="contents" placeholder="글 입력.."></textarea>
		</div>
		<div class="buttonbox">
			<label for="files" class="filebtn">업로드</label><input type="file" id="files" name="files" multiple="multiple" style="display: none;">
			<input type="text" class="filetext" value="선택된 파일 없음">
			<button type="submit" class="upbtn">저 장</button>
		</div>
	</form>
</div>
</body>
</html>

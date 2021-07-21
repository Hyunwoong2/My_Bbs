<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판 목록</title>
<style>
#container{width:fit-content;padding:20px; margin:0px auto;}
table { border-spacing: 0px; border-collapse: collapse;}
th,td{border-right: 1px solid black;padding:5px 10px ;text-align: center;}
th:nth-child(4){border-right:none;}
td:nth-child(4){border-right:none;}
td:nth-child(1){text-align: right;}
td:nth-child(2){text-align: left;}
th{border-bottom:3px double black;}
td{border-bottom:1px dotted black;}
tr:nth-child(even) { background-color: #eee;}
#container>h3{text-align: center; width:fit-content;
	margin:0px auto;margin-bottom:10px;}
#pagination{border-top:3px double black; width:fit-content; padding:10px; margin:10px auto;}
#userid{position: absolute; right: 100px;}
.log_a{position: absolute; left: 70px; text-decoration: none;}
.log_a:hover {color:gold;}
div#cbtn{position: relative; left : 250px; top :20px;}
</style>
</head>
<body>
<div id='userid'> ${userId} <a href="/mb/bbs/logout" class=log_a>logout</a></div>
<div id='container'>
<h3>게시판 목록</h3>
	<table class=ft>
		<tr><th>번 호</th><th>제 목</th><th>작성자</th><th>작성일</th></tr>
		<c:forEach var="b" items="${pageinfo.list}">
			<tr><td>${b.num}</td>
			<td><a href="/mb/bbs/getbbs?num=${b.num}">${b.title}</a></td>		
			<td>${b.writer}</td>
			<td>${b.wdate}</td></tr>
		</c:forEach>
	</table>
	<form action="/mb/bbs/find" method="post" class="findform">
		<select name="category">
			<option selected>제 목</option>
			<option>작 성 자</option>
		</select>
		<input type="text" name="keyword">
		<button type="submit">검색</button>
	</form>
	<div id="cbtn"><button type="button" onclick="location.href='/mb/bbs/boardform'">글 쓰기</button></div>
	<div id="pagination">
	<c:forEach var="i" items="${pageinfo.navigatepageNums}">
		<c:choose>
			<c:when test="${i==pageinfo.pageNum}">
				[${i}] 
			</c:when>
			<c:otherwise>
				[<a href="/mb/bbs/page/${i}">${i}</a>] 
			</c:otherwise>
		</c:choose> 
	</c:forEach>
</div>
</div>
</body>
</html>

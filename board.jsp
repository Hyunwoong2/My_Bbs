<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판 글 보기</title>
<style>
	#container{width:fit-content; padding:20px; margin:0px auto;}
	#container>h3{text-align:center;}
	#board{border-radius: 15px;}
	#board>div{padding:0px 5px;}
	.row{border:1px solid black;}
	.row>label{display:inline-block; width:50px; padding:5px 5px; 
		border-right:1px dotted black; margin-right:10px;}
	.row>label:not(:first-child){border-left:3px double black;}
	#contents{ overflow: auto; width:700px; height:100px; border:1px solid black;}
	.hcontents{ overflow: auto; width:700px; height:100px; border:1px solid black;}
	div#comdiv textarea{ overflow: auto; width:700px; height:100px; border:1px solid black;}
	.inputdiv{width:fit-content;margin:0px auto;}
	#hierarchy{width:700px; padding:20px; margin:0px auto;}
	div.comments{border-top:1px solid black;}
	.cb{float: right;}
	.cbb{float: right;}	
	#userid{position: absolute; right: 100px;}
	.log_a{position: absolute; left: 70px; text-decoration: none;}
	.log_a:hover {color:gold;}
	a{text-decoration: none;}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script>

$(function(){
	$(".comdiv").css("display","none");
	$(".comup").css("display","none");
	$(".cb").click(function(){
		$(".comup").css("display","none");
		$(".comdiv").css("display","none");
		$("div.inputdiv").css("display","none");
		$("#"+this.id+"1").css("display","block");
	});
	$(".cbb").click(function(){
		$(".comup").css("display","none");
		$("div.inputdiv").css("display","none");
		$(".comdiv").css("display","none");
		$("."+this.id+"hup").css("display","none");
		$("#"+this.id+"up").css("display","block");
	});
	$(".cmup").click(function(){
		var param = new Object();
		param.num = $('#'+this.id+'nup').val();
		param.title = $('#'+this.id+'tup').val();
		param.contents = $('#'+this.id+'cup').val();
		alert(JSON.stringify(param));
		$.ajax({
			url:'/mb/bbs/update',
			method:'post',
			data:param,
			dataType:'text',
			success:function(res){
				alert("up확인");
				location.href=''
			},
			error:function(xhr,status,error){
				alert(status+':'+error);
			}
		});
	});
	$(".cmad").click(function(){
		var param = new Object();
		param.pnum = $('#'+this.id+'n').val();
		param.writer = $('#'+this.id+'c').val();
		param.contents = $('#'+this.id+'d').val();
		alert(JSON.stringify(param));
		$.ajax({
			url:'/mb/bbs/hwrite',
			method:'post',
			data:param,
			dataType:'text',
			success:function(res){
				alert("res 성공");
				location.href=''
			},
			error:function(xhr,status,error){
				alert(status+':'+error);
			}
		});
	});
});
function addform(){
	var param = new Object();
	param.pnum = $('#pnum').val();
	param.writer = $('#writer').val();
	param.contents = $('textarea#contents').val();
	$.ajax({
		url:'/mb/bbs/write',
		method:'post',
		data:param,
		dataType:'text',
		success:function(res){
			location.href=''
		},
		error:function(xhr,status,error){
			alert(status+':'+error);
		}
	});
}
function del(num,pnum){
	if(!confirm("정말로 삭제하시겠어요?")) return;
	param = {}
	param.num = num;
	$.ajax({url: '/mb/bbs/delete',
	data:param,
	method:'post',
	dataType:'text',
	success:function(res){
		if(eval(res)){
			alert("삭제 성공");
			location.href="/mb/bbs/getbbs?num="+pnum;
		}
		else{
			alert("삭제 실패");
		}
	},
	error:function(xhr,status,error){
		alert(status+":"+error);
	}
	});
}
</script>
</head>
<body>
<div id='userid'> ${userId} <a href="/mb/bbs/logout" class=log_a>logout</a></div>
<div id='container'>
	<h3>게시판 글 보기</h3>
	<div id='board'>
		<div class='row'><label>제목</label>${vo.title}</div>
		<div class='row'>
		<label>번호</label>${vo.num}
		<label>작성자</label>${vo.writer}
		<label>작성일</label>${vo.wdate}
		<label>히트수</label>${vo.hit}
		</div>
		<div id="contents" class="row">
			<div id = "downloadfile">
				<c:forEach var="fvo" items="${download}">
					<a href="/mb/bbs/download/${fvo.filename}">${fvo.filename}</a> 
				</c:forEach>
			</div>
		${vo.contents}
		</div>
	</div>
</div>



<div id='hierarchy'>
	<h4>Comments</h4>
	<div>
		<c:forEach var="line" items="${list}">
		<div class="comments">
			<div>			
				<div>${line.writer} ${line.wdate}<button type="button" id="${line.writer}" class="cb">답 변</button>
				<c:if test="${userId eq line.writer}">
						<button type="button" id="${line.writer}" class= "cbb">수 정</button>
						<button type="button" id="delbtn" class= "cbb" onclick="del(${line.num},${vo.num})">삭 제</button>
				</c:if>
				</div>								
				
				<div class="${line.writer}hup">${line.contents}</div>
				
				<div class="comup" id="${line.writer}up">					
					<form action="/mb/bbs/update" method="post">
					<input type="hidden" name="num" id="${line.writer}nup" value="${line.num}">
					<input type="hidden" name="title" id="${line.writer}tup" v

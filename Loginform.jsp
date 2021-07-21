<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인 폼</title>
<style>
	*{ margin: 0px; padding: 0px; text-decoration: none; font-family:sans-serif; }
	#loginform {position:absolute; width:300px; height:400px; padding:30px, 20px; 
	background-color:#EBFBFF;  text-align:center;  top:50%;  left:50%; border : 1px solid black; 
	transform: translate(-50%,-50%);  border-radius: 15px;}
	#loginform h2{text-align: center; margin: 30px;}
	.idForm{ border-bottom: 2px solid #adadad; margin: 30px; padding: 10px 10px;}
	.passForm{ border-bottom: 2px solid #adadad; margin: 30px; padding: 10px 10px;}
	#id {width:100%; border:none; outline:none; color: #636e72; font-size:16px; height:25px; background: none;}
	#pw {width:100%; border:none; outline:none; color: #636e72; font-size:16px; height:25px; background: none;}
	.btn { position:relative; left:40%; transform: translateX(-50%); margin-bottom: 40px; width:80%; height:40px;	
 	background: linear-gradient(grey, 10%, pink); background-position: left;  background-size: 200%;
 	color:black; font-weight: bold; border:none; cursor:pointer; transition: 0.4s; display:inline;}
</style>
</head>
<body>
<div id="loginform">
<h2>Login</h2>
	<form action="/mb/bbs/login" method="post">
		<div class="idForm"><label for="id">ID</label><input type="text" id="id" name="id"></div>
		<div class="passForm"><label for="pw">PW</label> <input type="password" id="pw" name="pw"></div>
		<div><button type="submit" class="btn">LOG IN</button></div>
	</form>
</div>
</body>
</html>

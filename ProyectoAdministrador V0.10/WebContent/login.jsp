<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Title Page</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<!--Pulling Awesome Font -->
		<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="less/LoginAdmin.css">
	</head>
	<body>
		<div id="modall"></div>
		<div class="container">
		    <div class="row">
		        <div class="col-md-offset-4 col-md-4">
		            <div class="form-login">
		            <h4>Bienvenido.</h4>
		            <input type="text" id="userName" class="form-control input-sm chat-input" placeholder="username" />
		            </br>
		            <input type="password" id="userPassword" class="form-control input-sm chat-input" placeholder="password" />
		            </br>
		            <div class="wrapper">
		            <span class="group-btn">     
		                <button type="button" id="btnLoginOperario" class="btn btn-primary">login</button>  
		            </span>
		            </div>
		            </div>		        
		        </div>
		    </div>
		</div>

		<script src="./js/jquery-1.11.3.min.js"></script>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/md5.js"></script>
		<script src="./js/loginAdmin.js"></script>
	</body>
</html>
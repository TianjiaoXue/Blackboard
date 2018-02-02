<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student View Course</title>

<style>
h1 {
	color: #558B2F;
	font-family: verdana;
	font-size: 300%;
	text-align: center;
}

a {
	color: #FB8C00;
	font-family: verdana;
	font-size: 100%;
	text-align: center;
}

body, html,{
height:100%;
width:95%;
min-height:100%;
}
.container {
	max-height: 600px;
	min-height: 600px;
	max-width: 1200px;
	min-width: 1200px;
}
</style>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<div class="row">
   
        <ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:history.back(-1)">Go back</a></li>
				<li><a href="${contextPath}">Log Out</a></li>
		</ul>
        </div>
        </div>
        </nav>
 <div class="container">
	

	<div>
	<h2>Welcome to chart board </h2>
		<textarea rows="20" cols="130" id="content"></textarea>
		<br> <input type="text" style="width: 500px; height: 30px" id="new" name="new" />
		<input type="button" value="Send" onclick="sendMessage()" />

	</div>

</div>
	<script type="text/javascript">
		window.setInterval(getMessage, 5000);

		function getMessage() {
      	var xhr = new XMLHttpRequest();			
			if (xhr) {
				xhr.open("POST", "getMessage.htm", true);
				xhr.onreadystatechange = function() {
					if (xhr.readyState == 4 && xhr.status == 200) {
						var messageResult = xhr.responseXML;
						var content = messageResult.getElementsByTagName("msg");
						var mess = "";
						var print = "";
						var msender = "";
						if (content.length != 0) {
							for (var i = 0; i < content.length; i++) {
								var m = content[i];
								time = m.getAttribute('p');
								msender = m.getAttribute("sender");
								mess = msender + " : " + m.getAttribute('mess') + " at " + time ;							
							}
							
								var a = document.getElementById("content");
								a.innerHTML += mess + "\r\n";
								a.scrollTop = a.scrollHeight;
							
						}
					}
				}
				xhr.send(null);
			}
		}

		function sendMessage() {

				var xhr = new XMLHttpRequest();
			
			if (xhr) {
				var content = document.getElementById("new").value;
				
				var url = "sendMessage.htm" + "?content=" + content;

				xhr.open("POST", url, true);

				xhr.onreadystatechange = function() {
					if (xhr.readyState == 4 && xhr.status == 200) {

					}
				}
				xhr.send();
			}
			
			var ul = document.getElementById("content");
			ul.innerHTML += content + "\r\n";
			ul.scrollTop = ul.scrollHeight;
		}
	</script>

</body>
</html>
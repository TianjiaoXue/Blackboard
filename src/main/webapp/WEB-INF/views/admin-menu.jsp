<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>admin-menu</title>
<style>
a {
	color: #FB8C00;
	font-family: verdana;
	font-size: 100%;
	text-align: center;
}

body
,
html
,
{
height
:
 
100%;
width
:
95%;

	
min-height
:
 
100%;
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


	<div class="navbar navbar-custom">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<div class="row">
			<div class="navbar-brand">
				<p>Hello Admin</p>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${contextPath}/admin/addCourse.htm">Add New
						Course</a><br></li>
				<li><a href="${contextPath}/admin/viewCourse.htm">View
						Course catalog</a><br></li>
				<li><a href="${contextPath}/admin/manageUser.htm">Manage
						Users</a><br></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:history.back(-1)">Go back</a></li>
				<li><a href="${contextPath}">Log Out</a></li>
			</ul>
		</div>

	</div>

	<div class="container">
		<h1>This is Admin Main Page</h1>
	</div>


</body>
</html>
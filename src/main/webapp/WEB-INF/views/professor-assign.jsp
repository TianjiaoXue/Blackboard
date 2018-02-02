<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Professor post Assignment</title>
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

	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<div class="row">

			<ul class="nav navbar-nav">




				<li><a href="${contextPath}/professor/addCourse.htm">Choose
						Course</a></li>

				<li><a href="${contextPath}/professor/viewCourse.htm">View
						Course</a></li>

				<li><a href="${contextPath}/professor/assignment.htm">Add
						Assignment</a></li>
<li><a href="${contextPath}/professor/selectCourseAssign">View Assignment</a></li>

				<li><a href="${contextPath}/message.htm">View messages</a></li>

				<li><a href="${contextPath}/userAccount.htm">Manage user
						account</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:history.back(-1)">Go back</a></li>
				<li><a href="${contextPath}">Log Out</a></li>
			</ul>
		</div>
	</div>
	</nav>
<div class="container">

	<h2>Post Assignment</h2>

	<form:form action="${contextPath}/professor/assignment" method="post"
		commandName="assignment">
		<table class="table table-striped">



			<tr>
				<td>Course:</td>
				<td><select name="course">
				<c:forEach var="course"  items="${courseList}">
						<option>${course.courseTitle}</option>
				</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Score:</td>
				<td><form:input type="text" path="score" size="30"
						required="required" /></td>
			</tr>
			
			<tr>
				<td>Description</td>
				<td><form:input type="textarea" path="description" size="30"
						required="required" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Post Assignment" /></td>
			</tr>
		</table>
	</form:form>
	</div>
</body>
</html>
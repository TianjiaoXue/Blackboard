<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin add Course</title>
<style>

h1{
    color:#558B2F;
    font-family: verdana;
    font-size: 300%;
    text-align: center;
}

a{  color:#FB8C00;
    font-family: verdana;
    font-size: 100%;
    text-align: center;
   
}
          
body, html, {
	height: 100%;
	width:95%;
	min-height: 100%;
}


.container
{
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
					<li><a href="${contextPath}/admin/addCourse.htm">Add New
							Course</a><br></li>
					<li><a href="${contextPath}/admin/viewCourse.htm">View
							Course catalog</a><br></li>
					<li><a href="${contextPath}/admin/manageUser.htm">Manage
							Users</a><br></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
                  <li><a href="javascript:history.back(-1)">Go back</a> </li>
                  <li><a href="${contextPath}">Log Out</a></li>
                 </ul>
					</div>
					
	</div>
	</nav>
			<div class="container" >

	<form:form action="${contextPath}/admin/addCourse" method="post" commandName="course">

		<table class="table table">
			<tr>
				<td>CourseTitle:</td>
				<td><form:input path="courseTitle" size="30"
						required="required" /> <font color="red"><form:errors
							path="courseTitle" /></font></td>
	    	</tr>
	    	
	    	<tr>
			   	<td>Semester:</td>
				<td><form:input path="semester" size="30"
						required="required" /> <font color="red"><form:errors
							path="semester" /></font></td>
			</tr>
			
			<tr>
			   	<td>Credit:</td>
				<td><form:input path="credit" size="30"
						required="required" /> <font color="red"><form:errors
							path="credit" /></font></td>
			</tr>
			
			<tr>
			   	<td>Capacity:</td>
				<td><form:input path="capacity" size="30"
						required="required" /> <font color="red"><form:errors
							path="capacity" /></font></td>
			</tr>
           

			<tr>
				<td colspan="2"><input type="submit" value="Create Course" /></td>
			</tr>
		</table>

	</form:form>
</div>
</body>
</html>
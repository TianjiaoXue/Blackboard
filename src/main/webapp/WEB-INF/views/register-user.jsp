<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Register User</title>
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

table{   
    border-collapse: collapse;
    border-spacing: 0px;
    width: 910px;
    border: 0px;
    text-align: left;
}

th, td {padding: 10px;}
            
</style>
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}">Go Back</a>
	<br />

	<h2>Register a New User</h2>

	<form:form action="${contextPath}/user/register" commandName="user"
		method="post">

		<table>

			<tr>
				<td>User Name:</td>
				<td><form:input path="username" size="30" required="required" pattern="^[A-Za-z0-9]{1-60}$" />
					<font color="red"><form:errors path="username" /></font></td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><form:password path="password" size="30"
						required="required" pattern="^[A-Za-z0-9]{1-60}$" /> <font color="red"><form:errors
							path="password" /></font></td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><form:input path="email" size="30" type="email"
						required="required" /> <font color="red"><form:errors
							path="email" /></font></td>
			</tr>

			<tr>
				<td>Role:</td>
				<td><select name="role">
						<option value="professor">Professor</option>
						<option value="student">Student</option>
						<option value="admin">admin</option>
						
				    </select> <font color="red"> <form:errors path="role" /></font></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Register User" /></td>
			</tr>
		</table>

	</form:form>

</body>
</html>
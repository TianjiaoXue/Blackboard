<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register-user</title>
<style type="text/css">
h1 {
    color:#00E676;
    font-family: verdana;
    font-size: 200%;
    text-align: center;
}
a{  color:#FB8C00;
    font-family: verdana;
    font-size: 100%;
    text-align: center;
   
}
</style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <h1>Save user information successfully! </h1>
    
     <a href="javascript:history.back(-1)">Go back</a>
     <a href="${contextPath}">Click to login Page</a>
</body>
</html>
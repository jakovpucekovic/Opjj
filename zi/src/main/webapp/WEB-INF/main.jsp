<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Grafika</title>
</head>
<body>

<ol>
<c:forEach var="el" items="${list}">
	<li><a href="${pageContext.request.contextPath}/grafika/show?id=${el}">${el}</a></li>
</c:forEach>
</ol>

<form action="${pageContext.request.contextPath}/grafika/save" method="get">	
<span>Title</span><input type="text" name="title" size="20">
<br>
<span>Body</span><textarea name="text" rows="40" cols="80" name='body'></textarea>
<span>&nbsp;</span>
<input type="submit" name="save" value="Save">
		
</form>

</body>
</html>
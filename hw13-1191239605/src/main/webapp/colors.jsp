<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Background color chooser</title>
</head>
<body bgcolor="${pickedBgCol}">
	<p>Choose a background color.</p>
	<br>
	<a href="${pageContext.request.contextPath}/setcolor?bgcolor=white">WHITE</a>
	<a href="${pageContext.request.contextPath}/setcolor?bgcolor=red">RED</a>
	<a href="${pageContext.request.contextPath}/setcolor?bgcolor=green">GREEN</a>
	<a href="${pageContext.request.contextPath}/setcolor?bgcolor=cyan">CYAN</a>
	
	
	
</body>
</html>
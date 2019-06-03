<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<body bgcolor="${pickedBgCol}">
	<a href="${pageContext.request.contextPath}/colors.jsp">Background color chooser</a>
	<br>	
	<form action="trigonometric" method="GET">
 	  Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
	  Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
	  <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<br>
	<a href="${pageContext.request.contextPath}/stories/funny.jsp">Click here for a funny story</a>
</body>
</html>
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
	<a href="${pageContext.request.contextPath}/trigonometric?a=0&b=90">Click here for trigonometric values from 0 to 90 degrees</a>	
	<br>
	<form action="trigonometric" method="GET">
 	  Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
	  Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
	  <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<br>
	<a href="${pageContext.request.contextPath}/stories/funny.jsp">Click here for a funny story</a>
	<br>
	<a href="${pageContext.request.contextPath}/appinfo.jsp">Current running time</a>
	<br>
	<a href="${pageContext.request.contextPath}/powers?a=1&b=100&n=3">Download powers.xls</a>
	
</body>
</html>
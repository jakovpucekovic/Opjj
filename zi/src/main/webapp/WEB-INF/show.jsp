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

<h1>${title}</h1>

<p>Lines: ${line}</p>
<p>Circles: ${circle }</p>
<p>Filled circles: ${fcircle }</p>
<p>Filled triangle: ${ftriangle }</p>



<a href="${pageContext.request.contextPath}/grafika/main"></a>

</body>
</html>
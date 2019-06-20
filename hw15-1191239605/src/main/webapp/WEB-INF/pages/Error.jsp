<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Error</title>
	</head>

	<body>
		<h1>An error happened</h1>
		<p><c:out value="${poruka}"/></p>

		<p><a href="servleti/main">Return to homepage</a></p>
	</body>
</html>
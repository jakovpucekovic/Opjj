<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Homepage</title>
	</head>

	<body>
		<h1>List of registered authors</h1>
		<c:choose>
			<c:when test="${blogUsers.isEmpty()}">
				<p>There are no registered authors.</p>
			</c:when>
			<c:otherwise>
			<ol>
			<c:forEach var="user" items="${blogUsers}">
			<li>
			  <c:out value="${user.nick}"/> 
			</li>
			</c:forEach>
			</ol>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>
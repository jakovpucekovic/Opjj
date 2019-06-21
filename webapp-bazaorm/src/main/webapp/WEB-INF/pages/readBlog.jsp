<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>${blogEntry.title}</title>
	</head>

	<body>
		<header>
		<c:choose>
			<c:when test="${not empty sessionScope[\"current.user.id\"]}">
	        <h2>Hello, ${sessionScope["current.user.fn"]} ${sessionScope["current.user.ln"]}</h2>
	        <h3><a href="${pageContext.request.contextPath}/servleti/logout">Logout</a></h3>
		    </c:when>
		    <c:otherwise>
		    	<p>Not logged in.</p>
		    </c:otherwise>
		</c:choose>   
		</header>
		
	<h2>${blogEntry.title}</h2>
	<p>${blogEntry.text}</p>
	
	<c:if test="${sessionScope[\"current.user.nick\"].equals(blogEntry.creator.nick)}">
			<h3><a href="${pageContext.request.contextPath}/servleti/author/${sessionScope['current.user.nick']}/edit">Edit blog</a></h3>
	</c:if>
	
	<c:forEach var="comment" items="${blogEntry.comments}">
		<h3>${comment.l}</h3>
    	<h4>${comment.postedOn}</h4>
    	<p>${comment.message}</p>
	</c:forEach>
	
	
	<p>Add comment</p>
		
	<a href="${pageContext.request.contextPath}/servleti/main">Back to homepage</a>
		
	</body>
</html>
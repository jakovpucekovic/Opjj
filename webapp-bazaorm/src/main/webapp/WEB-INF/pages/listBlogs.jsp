<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Blogs</title>
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
	
		<h1>List of blogs</h1>
		<c:choose>
			<c:when test="${blogEntries.isEmpty()}">
				<p>There are no blogs by this author.</p>
			</c:when>
			<c:otherwise>
			<ol>
			<c:forEach var="entry" items="${blogEntries}">
			<li>
			  <a href="${pageContext.request.contextPath}/servleti/author/${entry.creator}/${entry.id}">${entry.title}</a> 
			</li>
			</c:forEach>
			</ol>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${not empty sessionScope[\"current.user.id\"] and sessionScope[\"current.user.nick\"] == currentPageAuthor} ">
			<a href="${pageContext.request.contextPath}/servleti/author/${sessionScope['current.user.nick']}/new">Create new blog</a>
		</c:if>
		
	</body>
</html>
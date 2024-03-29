<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Blog entry</title>
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
	
		<h1>${addOrEdit} blog entry</h1>
		
		<c:choose>
		<c:when test="${addOrEdit == 'add'}">
			<form action="${pageContext.request.contextPath}/servleti/author/${sessionScope['current.user.nick']}/new" method="post">	
		</c:when>
		<c:otherwise>
			<form action="${pageContext.request.contextPath}/servleti/author/${sessionScope['current.user.nick']}/edit/${blogEntryForm.id}" method="post">			
		</c:otherwise>
		</c:choose>

		<div>
		 <div>
		  <span>Title</span><input type="text" name="title" value='<c:out value="${blogEntryForm.title}"/>' size="20">
		 </div>
		 <c:if test="${blogEntryForm.hasError('title')}">
		 <div><c:out value="${blogEntryForm.getError('title')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span>Body</span><textarea name="text" rows="40" cols="80">${blogEntryForm.text}</textarea>
		 </div>
		 <c:if test="${blogEntryForm.hasError('lastName')}">
		 <div><c:out value="${blogEntryForm.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		  <span>&nbsp;</span>
		  <input type="submit" name="method" value="Save">
		  <input type="submit" name="method" value="Back">
		</div>
		
		</form>

	</body>
</html>

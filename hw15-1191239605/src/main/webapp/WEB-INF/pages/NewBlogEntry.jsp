<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>New blog entry</title>
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
	
		<h1>Add new blog entry</h1>

		<form action="${pageContext.request.contextPath}/servleti/author/NICK/new" method="post">

		<div>
		 <div>
		  <span>Title</span><input type="text" name="title" value='<c:out value="${entryForm.title}"/>' size="20">
		 </div>
		 <c:if test="${entryForm.hasError('title')}">
		 <div><c:out value="${userForm.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span>Body</span><textarea name="text" rows="40" cols="80">${userForm.text}</textarea>
		 </div>
		 <c:if test="${userForm.hasError('text')}">
		 <div><c:out value="${userForm.getError('text')}"/></div>
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

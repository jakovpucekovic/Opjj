<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>New blog entry</title>
	</head>

	<body>
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
		 <c:if test="${userForm.hasError('lastName')}">
		 <div><c:out value="${userForm.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		  <span>&nbsp;</span>
		  <input type="submit" name="method" value="Register">
		  <input type="submit" name="method" value="Back">
		</div>
		
		</form>

	</body>
</html>

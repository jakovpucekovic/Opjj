<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
	</head>

	<body>
		<h1>New user</h1>

		<form action="${pageContext.request.contextPath}/servleti/register" method="post">

		<div>
		 <div>
		  <span>First name</span><input type="text" name="firstName" value='<c:out value="${userForm.firstName}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('firstName')}">
		 <div><c:out value="${userForm.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span>Last name</span><input type="text" name="lastName" value='<c:out value="${userForm.lastName}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('lastName')}">
		 <div><c:out value="${userForm.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span>Nick</span><input type="text" name="nick" value='<c:out value="${userForm.nick}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('nick')}">
		 <div><c:out value="${userForm.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span>EMail</span><input type="text" name="email" value='<c:out value="${userForm.email}"/>' size="50">
		 </div>
		 <c:if test="${userForm.hasError('email')}">
		 <div><c:out value="${userForm.getError('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span>Password</span><input type="password" name="password" value='<c:out value="${userForm.password}"/>' size="50">
		 </div>
		 <c:if test="${userForm.hasError('password')}">
		 <div><c:out value="${userForm.getError('password')}"/></div>
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

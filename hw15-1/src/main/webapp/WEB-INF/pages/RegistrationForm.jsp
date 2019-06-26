<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
	</head>

	<body>
		<h1>New user</h1>

		<form action="save" method="post">

		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${userForm.firstName}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('firstName')}">
		 <div class="greska"><c:out value="${userForm.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${userForm.lastName}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('lastName')}">
		 <div class="greska"><c:out value="${userForm.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${userForm.nick}"/>' size="20">
		 </div>
		 <c:if test="${userForm.hasError('nick')}">
		 <div class="greska"><c:out value="${userForm.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${userForm.email}"/>' size="50">
		 </div>
		 <c:if test="${userForm.hasError('email')}">
		 <div class="greska"><c:out value="${userForm.getError('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="text" name="password" value='<c:out value="${userForm.password}"/>' size="50">
		 </div>
		 <c:if test="${userForm.hasError('password')}">
		 <div class="greska"><c:out value="${userForm.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Register">
		  <input type="submit" name="method" value="Back">
		</div>
		
		</form>

	</body>
</html>

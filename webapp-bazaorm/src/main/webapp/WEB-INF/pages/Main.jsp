<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Homepage</title>
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
		
		<c:if test="${empty sessionScope[\"current.user.id\"]}">
			<h1>Login</h1>
			<form action="${pageContext.request.contextPath}/servleti/login" method="post">
	
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
			  <span>Password</span><input type="text" name="password" value='<c:out value="${userForm.password}"/>' size="50">
			 </div>
			 <c:if test="${userForm.hasError('password')}">
			 <div><c:out value="${userForm.getError('password')}"/></div>
			 </c:if>
			</div>
	
			<div>
			  <span>&nbsp;</span>
			  <input type="submit" name="method" value="Login">
			</div>
			
			</form>
		</c:if>
	
	
		<h1>Click 
			<a href="${pageContext.request.contextPath}/servleti/register">here</a>
			to register</h1>
		
	
		<h1>List of registered authors</h1>
		<c:choose>
			<c:when test="${blogUsers.isEmpty()}">
				<p>There are no registered authors.</p>
			</c:when>
			<c:otherwise>
			<ol>
			<c:forEach var="user" items="${blogUsers}">
			<li>
			  <a href="${pageContext.request.contextPath}/servleti/author/${user.nick}">${user.nick}</a> 
			</li>
			</c:forEach>
			</ol>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>
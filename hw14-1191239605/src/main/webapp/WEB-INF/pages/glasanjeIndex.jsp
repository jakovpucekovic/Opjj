<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje</title>
</head>
<body>
  <h1>${poll.title}</h1>
  <p>${poll.message}</p>
  <ol>
  	<c:forEach var="candidate" items="${votingCandidates}">
		<li><a href="${pageContext.request.contextPath}/servleti/glasanje-glasaj?pollID=${poll.id}&id=${candidate.id}">${candidate.name}</a></li>
	</c:forEach>
  </ol>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje</title>
</head>
<body bgcolor="${pickedBgCol}">
  <h1>Glasanje za omiljeni bend</h1>
  <p>Od slijedecih bendova, koji Vam je bend najdrazi? Kliknite na link kako biste glasovali</p>
  <ol>
  	<c:forEach var="candidate" items="${votingCandidates}">
		<li><a href="glasanje-glasaj?id=${candidate.id}">${candidate.name}</a></li>
	</c:forEach>
  </ol>
</body>
</html>
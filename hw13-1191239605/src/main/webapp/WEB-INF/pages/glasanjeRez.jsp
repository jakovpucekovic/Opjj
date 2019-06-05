<%@page import="hr.fer.zemris.java.hw13.classes.VotingCandidate"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje-rezultati</title>
</head>
<body bgcolor="${pickedBgCol}">
	<h1>Rezultati glasanje</h1>
	<p>Ovo su rezultati glasanja.</p>
	
	<table border="1">
	  <tr>
	  	<th>Bend</th>
	  	<th>Broj glasova</th>
	  </tr>
	  
      <c:forEach var="candidate" items="${votingResults}">
	    <tr>
	      <td>${candidate.name}</td>	
		  <td>${candidate.votes}</td>
		</tr>	
	  </c:forEach>	
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="${pageContext.request.contextPath}/glasanje-grafika">
	
	<h2>Rezultati u XLS formatu</h2>
	
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
	
	<%
		List<VotingCandidate> cand = (List<VotingCandidate>) request.getAttribute("votingResults");
		cand.sort((a,b)-> b.getVotes() - a.getVotes());
		int max = cand.get(0).getVotes();
		for(VotingCandidate candidate : cand){
			if(candidate.getVotes() != max){
				break;
			}
			%><li><a href="<%=candidate.getSongLink()%>"><%=candidate.getName()%></a></li><% 
		}
	%>
	
	
	
	</ul>

</body>
</html>
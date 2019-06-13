<%@page import="hr.fer.zemris.java.p12.model.VotingCandidate"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje-rezultati</title>
</head>
<body>
	<h1>Rezultati glasanje</h1>
	<p>Ovo su rezultati glasanja.</p>
	
	<table border="1">
	  <tr>
	  	<th>Kandidat</th>
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
	<img alt="Pie-chart" src="${pageContext.request.contextPath}/servleti/glasanje-grafika?pollID=${param.pollID}">
	
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="${pageContext.request.contextPath}/servleti/glasanje-xls?pollID=${param.pollID}">ovdje</a></p>

	
	<h2>Razno</h2>
	<p>Linkovi pobjednika:</p>
	<ul>
	
	<%
		List<VotingCandidate> cand = (List<VotingCandidate>) request.getAttribute("votingResults");
		cand.sort((a,b)-> (int)(b.getVotes() - a.getVotes()));
		long max = cand.get(0).getVotes();
		for(VotingCandidate candidate : cand){
			if(candidate.getVotes() != max){
				break;
			}
			%><li><a href="<%=candidate.getLink()%>"><%=candidate.getName()%></a></li><% 
		}
	%>
	
	</ul>

</body>
</html>
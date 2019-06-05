<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page import="java.util.Date" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    
<%! private void calculateTime(javax.servlet.jsp.JspWriter out, long startTime) throws java.io.IOException {
		long timeDifference = System.currentTimeMillis() - startTime;
		String format = "%d days %d hours %d minutes %d seconds and %d miliseconds";
		//long days = timeDifference / (24*60*60*1000);
		//long hours = timeDifference - (days*24*60*60*1000);
		long minutes = timeDifference / 60 * 1000;
		long seconds = (timeDifference - minutes * 60 * 1000)/ 1000;
		long miliseconds = timeDifference - seconds * 1000;
		//out.write(String.format(format, days, hours, minutes, seconds, miliseconds));
	} 
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Current running time</title>
</head>
<body bgcolor="${pickedBgCol}">
  <h1>Current running time</h1>
  <p><% long t = (long) pageContext.getServletContext().getAttribute("startingTime"); calculateTime(out, t); %></p>

</body>
</html>
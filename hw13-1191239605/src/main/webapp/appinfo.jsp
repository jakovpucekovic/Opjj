<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page import="java.util.Date" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    
<%! private void calculateTime(javax.servlet.jsp.JspWriter out, long startTime) throws java.io.IOException {
		Date timeDifference = new Date(System.currentTimeMillis() - startTime);
		DateFormat format = new SimpleDateFormat("dd 'days' HH 'hours' mm 'minutes' ss 'seconds and' SSS 'miliseconds'");
		out.write(format.format(timeDifference));
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
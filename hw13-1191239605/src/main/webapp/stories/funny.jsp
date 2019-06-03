<%@ page import="java.util.Random" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    
    
<%! private void pickRandomColor(javax.servlet.jsp.JspWriter out) throws java.io.IOException {
		String[] colors = {"red", "black", "blue", "yellow", "pink", "green", "cyan", "indigo", "magenta"};
		Random rand = new Random();
		out.write(colors[rand.nextInt(colors.length)]);
	}
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="${pickedBgCol}">

	<p><font color="<% pickRandomColor(out); %>">
		This is a funny story.
	</font></p>	

</body>
</html>
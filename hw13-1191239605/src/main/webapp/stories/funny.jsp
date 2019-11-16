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
		George Phillips of Meridian, Mississippi was going up to bed when his wife told him that he'd left the light on in the garden shed, which she could see from the bedroom window.
		George opened the back door to go turn off the light but saw that there were people in the shed stealing things.
		He phoned the police, who asked "Is someone in your house?" and he said "No". Then they said that all patrols were busy, and that he should simply lock his door and an officer would be along when available.
		George said, "Okay," hung up, counted to 30, and phoned the police again.
		"Hello, I just called you a few seconds ago because there were people in my shed. Well, you don't have to worry about them now cause I've just shot them all." Then he hung up.
		Within five minutes three police cars, an Armed Response unit, and an ambulance showed up at the Phillips residence and caught the burglars red-handed.
		One of the Policemen said to George: "I thought you said that you'd shot them!"
		George said, "I thought you said there was nobody available!"
	</font></p>	

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trigonometric</title>
</head>
<body bgcolor="${pickedBgCol}">

<table border="1">
  <tr>
    <th>i</th>
    <th>sin(i)</th>
    <th>cos(i)</th>
  </tr>
  <c:forEach var="i" items="${trigList}">
  	<tr>
      <td>${i.value}</td>
      <td>${i.sin}</td>
      <td>${i.cos}</td>
  	</tr>	
  </c:forEach>
</table>


</body>
</html>
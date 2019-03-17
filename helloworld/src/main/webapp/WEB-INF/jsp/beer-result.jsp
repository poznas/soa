<%@ page import="com.agh.soa.lab2.BeerExpert" %>
<%@ page import="java.util.Optional" %>
<%@ page import="static java.util.Optional.ofNullable" %><%--
  Created by IntelliJ IDEA.
  User: MlodyDanon
  Date: 3/17/2019
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doradca Piwny</title>
</head>
<body>
<%
    BeerExpert expert = ofNullable(request.getAttribute("beerExpert"))
            .map(BeerExpert.class::cast)
            .orElseThrow();
%>

<h1>Zalecane Piwo:</h1>
<div><%= expert.getBearName()%></div>
</body>
</html>

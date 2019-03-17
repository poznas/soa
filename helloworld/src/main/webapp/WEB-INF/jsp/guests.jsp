<%--
  Created by IntelliJ IDEA.
  User: MlodyDanon
  Date: 3/17/2019
  Time: 8:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Guest comment Board</h1>
<form action="guest" method="post">
    Add comment: <input type="text" name="comment"/>
    <input type="submit"/>
</form>
<c:forEach items="${posts}" var="item">

    <h3>${item.guest.login}</h3>
    <h5>${item.guest.email}</h5>
    ${item.comment}<br>
</c:forEach>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lac
  Date: 25. 1. 13.
  Time: 오전 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/user/logout" method="post">
    <input type="submit" value="로그아웃 (form)">
</form>

<a href="/user/logout">로그아웃</a>


<hr>
<form action="/board/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" multiple="multiple">
    <input type="submit" value="업로드">
</form>
</body>
</html>

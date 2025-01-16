<%--
  Created by IntelliJ IDEA.
  User: lac
  Date: 25. 1. 10.
  Time: 오후 5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>영화 리뷰</h1>
<h3>로그인</h3>
<form action="/user/auth" method="post">
    아이디: <input type="text" name="username">
    비밀번호: <input type="password" name="password">

    <input type="submit" value="login">
</form>
<a href="/user/join">회원가입</a>
</body>
</html>

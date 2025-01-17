<%--
  Created by IntelliJ IDEA.
  User: lac
  Date: 25. 1. 16.
  Time: 오후 8:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<h1>로그인 페이지</h1>
<form action="/user/login" method="post">
    <div>
        <label for="username">아이디</label>
        <input type="text" name="username" id="username">
    </div>
    <div>
        <label for="password">비밀번호</label>
        <input type="password" name="password" id="password">
    </div>
    <input type="submit" value="로그인">
</form>
<a href="/user/register">회원가입</a>
</body>
</html>

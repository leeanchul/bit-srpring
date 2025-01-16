<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>index</h1>
<img src="/resources/pig.jpg" alt="no">
<form action="/user/auth" method="post">
    아이디: <input type="text" name="username">
    비밀번호: <input type="password" name="password">
    기억하기: <input type="checkbox" id="remember-me" name="remember-me">
    <input type="submit" value="login">
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<h1>login page</h1>
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

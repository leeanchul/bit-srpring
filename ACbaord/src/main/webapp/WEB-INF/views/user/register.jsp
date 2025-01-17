<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>
<h1>회원가입 페이지</h1>
<form action="/user/register" method="post">
    <div>
        <label for="username">아이디</label>
        <input type="text" name="username" id="username">
    </div>
    <div>
        <label for="password">비밀번호</label>
        <input type="password" name="password" id="password">
    </div>
    <div>
        <label for="email">이메일</label>
        <input type="email" name="email" id="email">
    </div>
    <div>
        <label for="nickname">닉네임</label>
        <input type="text" name="nickname" id="nickname">
    </div>
    <input type="submit" value="가입">
</form>
</body>
</html>

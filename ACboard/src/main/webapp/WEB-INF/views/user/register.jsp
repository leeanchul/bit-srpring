<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>
<h1>register page</h1>

<form action="/user/register">
    <div>
        <label for="username">아이디</label>
        <input type="text" name="username" id="username">
    </div>

    <div>
        <label for="password">비밀번호</label>
        <input type="password" name="password" id="password">
    </div>

    <div>
        <label for="nickname">별명 및 이름</label>
        <input type="text" name="nickname" id="nickname">
    </div>

    <div>
        <label for="email">이메일</label>
        <input type="email" name="email" id="email">
    </div>
    <input type="submit" value="가입">
</form>

</body>
</html>

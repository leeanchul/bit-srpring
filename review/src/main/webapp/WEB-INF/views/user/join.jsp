<%@ page pageEncoding="UTF-8" %>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<head>
    <title>회원 가입하기</title>
</head>
<body>

<div class="container-fluid">
    <form action="/user/join" method="post">
        <div class="row mt-5 justify-content-center">
            <div class="col-2">
                <label for="input_username"> 아이디 </label>
            </div>
            <div class="col-5">
                <input class="form-control" type="text" name="username" id="input_username">
            </div>
        </div>

        <div class="row mt-5 justify-content-center">
            <div class="col-2">
                <label for="input_password"> 비밀번호 </label>
            </div>
            <div class="col-5">
                <input class="form-control" type="password" name="password" id="input_password">
            </div>
        </div>

        <div class="row mt-5 justify-content-center">
            <div class="col-2">
                <label for="input_nickname"> 닉네임 </label>
            </div>
            <div class="col-5">
                <input class="form-control" type="text" name="nickname" id="input_nickname">
            </div>
        </div>
        <div class="row mt-5 justify-content-center">
            <div class="col-3">
                <input class="btn btn-outline-primary" type="submit" value="가입하기">
            </div>
        </div>
    </form>

</div>
</body>
</html>
<%@ page pageEncoding="UTF-8" %>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<head>
  <title>영화 추가하기</title>
</head>
<body>

<div class="container-fluid">
  <form action="/movie/insert" method="post">
    <div class="row mt-5 justify-content-center">
      <div class="col-2">
        <label for="movieName"> 영화 제목 </label>
      </div>
      <div class="col-5">
        <input class="form-control" type="text" name="title" id="movieName">
      </div>
    </div>

    <div class="row mt-5 justify-content-center">
      <div class="col-2">
        <label for="director"> 감독 </label>
      </div>
      <div class="col-5">
        <input class="form-control" type="text" name="director" id="director">
      </div>
    </div>

    <div class="row mt-5 justify-content-center">
      <div class="col-2">
        <label for="content"> 줄거리 </label>
      </div>
      <div class="col-5">
        <input class="form-control" type="text" name="content" id="content">
      </div>
    </div>
    <div class="row mt-5 justify-content-center">
      <div class="col-3">
        <input class="btn btn-outline-primary" type="submit" value="추가하기">
      </div>
    </div>
  </form>

</div>
</body>
</html>
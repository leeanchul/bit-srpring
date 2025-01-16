
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>제목: ${movieDTO.title}</h1>
<h2>감독: ${movieDTO.director}</h2>
<h3>줄거리: ${movieDTO.content}</h3>
<p>개봉일:  <fmt:formatDate value="${movieDTO.entryDate}" pattern="yy년 MM월 dd일"/></p>
<h4>평균 별점 : ${scoreAll}</h4>
<br>
<hr>

<form action="/scope/insert/${movieDTO.id}" method="post">
    <label for="score">별점</label>
    <input type="number" name="score" max="5" min="1" id="score">
    <input type="submit" value="평가">
</form>
<br>
<c:if test="${role eq 'ADMIN'}">
    <a href="/movie/update/${movieDTO.id}">수정 하기</a>
    <a href="/movie/delete/${movieDTO.id}">삭제 하기</a>
</c:if>

<c:if test="${role eq 'ROLE_ADMIN' or role eq 'ROLE_REVIEWER'}">
    <hr>
    <form action="/review/insert/${movieDTO.id}" method="post">
        <lable for="content"> 코멘트 </lable>
        <input type="text" name="content">
        <input type="submit" value="작성">
    </form>
</c:if>

<br>
<hr>
<c:forEach items="${reviewList}" var="r">
    <p>${r.content} 님의 코멘트</p>
    <p>${r.nickname}</p>
    <fmt:formatDate value="${r.entryDate}" pattern="yy년 MM월 dd일"/>
    <a href="/review/delete/${r.id}"> 삭제 </a>
    <hr>
</c:forEach>
</body>
</html>

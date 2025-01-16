
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/">인덱스</a>
<h1>영화 리스트</h1>
<c:if test="${role eq 'ADMIN'}">
    <a href="/movie/insert">영화 추가</a>
</c:if>

<table>
    <thead>
    <tr>
        <td>영화제목</td>
        <td>감독</td>
        <td>줄거리</td>
        <td>개봉일</td>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${list}" var="b">
            <tr>
                <td>${b.title}</td>
                <td><a href="/movie/movieOne/${b.id}">${b.director}</a></td>
                <td>${b.content}</td>
                <td>  <fmt:formatDate value="${b.entryDate}" pattern="yy년 MM월 dd일"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>

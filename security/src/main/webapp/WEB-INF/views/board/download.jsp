<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<style>
    img{
        width: 100px;
        height: 100px;
    }
</style>
<head>
    <title>Title</title>
</head>

<body>
<a href="/board/upload">파일 업로드</a>
<hr>
<c:forEach items="${list}" var="i">
    <h1>${i.fileName}</h1>
   <!-- <img src="/board/upload/${i.filePath}" alt="${i.fileName}"> -->
    <img src="/upload/${i.filePath}" alt="${i.fileName}">
</c:forEach>

</body>
</html>

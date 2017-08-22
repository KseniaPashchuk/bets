<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../resources/css/error.css">
    <title>We've got some trouble</title>
</head>

<body>
<div class="cover">
    <h1>Resource not found
        <small>Error ${pageContext.errorData.statusCode}</small>
    </h1>
    <div class="error-item">Request from: ${pageContext.errorData.requestURI} is failed</div>
    <div class="error-item">Servlet name: ${pageContext.errorData.servletName}</div>
    <div class="error-item">Exception: ${pageContext.exception}</div>
    <div class="error-item">Message from exception: ${pageContext.exception.message}</div>
</div>
<footer>
    <p>Technical Contact: <a href=mailto:x@example.com>x@example.com</a></p>
</footer>
</body>
</html>
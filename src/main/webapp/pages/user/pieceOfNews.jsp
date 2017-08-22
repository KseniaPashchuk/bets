<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="post">
    <div class="post-info">
        <div class="post-title">${title}</div>
        <div class="post-meta"><span class="fa fa-clock-o"><i class="icon-time"> </i>
            <time class="entry-date published">${date}</time></span>
        </div>
    </div>
    <img class="news-pic" src="${pageContext.request.contextPath}/image/news/${picture}"
         alt="football">
    <div class="post-text">
        ${text}
    </div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<div class="post" id="news-post">
    <div class="post-info">
        <div class="clearfix">
            <div class="post-title">${title}</div>
            <div class="btn-group news-edit-btn-group clearfix">
                <button type="button" class="btn-edit"><span class="glyphicon glyphicon-pencil"></span></button>
                <button type="button" class="btn-delete"><span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
        <div class="post-meta"><span class="fa fa-clock-o"><i class="icon-time"> </i>
            <time class="entry-date published">${date}</time></span>
        </div>
    </div>

    <form class="news-picture" action="${pageContext.servletContext.contextPath}/controller"
          method="post" enctype="multipart/form-data">
        <input type="hidden" name="command" value="edit_news_picture"/>
        <input type="hidden" name="news_id" value="${id}"/>
        <img class="news-pic" id="news-pic" src="${pageContext.request.contextPath}/image/news/${picture}"
             alt="football">
        <div class="btn-group">
            <label class="btn-action" for="news-picture"><fmt:message key="common.btn.choose"/></label>
            <input type="submit" id="saveAvatar" value="<fmt:message key="common.btn.save"/>">
        </div>
        <input type="file" name="edit_news_picture" id="news-picture" style="display: none">
    </form>

    <div class="post-text">${text}</div>
</div>
<form class="edit-news-form" style="display: none;" id="edit-news"
      action="${pageContext.servletContext.contextPath}/controller" method="POST">
    <input type="hidden" name="command" value="edit_news"/>
    <input type="hidden" name="news_id" value="${id}"/>
    <input class="input-text" type="text" name="edit_title" id="edit_title" value="${title}">
    <textarea class="input-text text-area" name="edit_text" id="edit_text" cols="0" rows="0">${text}</textarea>
    <div class="btn-group">
        <input type="submit" value='<fmt:message key="common.btn.save"/>'>
        <input class="cancel-edit-news" type="button" value='<fmt:message key="common.btn.cancel"/>'>
    </div>
</form>
<div class="popup-wrap" id="delete-news-popup">
    <div class="popup-holder delete-news-holder" id="delete-news" style="display: none">
        <form class="delete-news-form clearfix" action="${pageContext.servletContext.contextPath}/controller"
              method="POST">
            <input type="hidden" name="command" value="delete_news"/>
            <h3><fmt:message key="admin.delete_question"/></h3>
            <input class="input-text" type="text" name="delete_title" id="delete-news-title" value=""
                   readonly="readonly">
            <div class="btn-group">
                <input type="submit" value='<fmt:message key="common.btn.delete"/>'>
                <input class="cancel-delete-news" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close cancel-delete-news"><a href="">x</a></div>
    </div>
</div>

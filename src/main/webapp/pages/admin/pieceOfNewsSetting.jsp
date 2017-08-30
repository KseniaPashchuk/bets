<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<div class="post" id="news-post"
     <c:if test="${(btg:contains(errors,'invalidNewsTitleError' ))||
     (btg:contains(errors,'invalidNewsTextError' ))}">style="display:none;"</c:if>>
    <p class="error-label"
       <c:if test="${!btg:contains(errors,'editNewsError' )}">style="display:none;"</c:if>>
        <fmt:message key="admin.news.edit.error"/>
    </p>
    <p class="error-label"
       <c:if test="${!btg:contains(errors,'deleteNewsError' )}">style="display:none;"</c:if>>
        <fmt:message key="admin.news.delete.error"/>
    </p>
    <p class="error-label"
       <c:if test="${!btg:contains(errors,'updateNewsPictureError' )}">style="display:none;"</c:if>>
        <fmt:message key="admin.news.update_picture.error"/>
    </p>
    <div class="post-info">
        <div class="clearfix">
            <div class="post-title" style="float: left;">${title}</div>
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
        <input type="hidden" name="newsId" value="${newsId}"/>
        <input type="hidden" name="title" value="${title}"/>
        <img class="news-pic" id="news-pic" src="${pageContext.request.contextPath}/image/news/${picture}"
             alt="football">
        <div class="btn-group">
            <label class="btn-action" for="news-picture"><fmt:message key="common.btn.choose"/></label>
            <input type="submit" id="saveAvatar" value="<fmt:message key="common.btn.save"/>">
        </div>
        <input type="file" name="picture" id="news-picture" style="display: none">
    </form>
    <div class="post-text">${text}</div>
</div>
<form class="edit-news-form" id="edit-news" onsubmit="return validateEditNewsForm()"
      action="${pageContext.servletContext.contextPath}/controller" method="POST"
      <c:if test="${(!btg:contains(errors,'invalidNewsTitleError' ))||(!btg:contains(errors,'invalidNewsTextError' ))}">style="display:none;"</c:if>>
    <input type="hidden" name="command" value="edit_news"/>
    <input type="hidden" name="newsId" value="${newsId}"/>
    <input class="input-text <c:if test="${!btg:contains(errors,'invalidNewsTitleError' )}">error</c:if>" type="text" name="title" id="edit-title" value="${title}">
    <p class="error-label" id="edit-invalid-title"
       <c:if test="${!btg:contains(errors,'invalidNewsTitleError' )}">style="display:none;"</c:if>>
        <fmt:message key="admin.news.invalid_title"/>
    </p>
    <textarea class="input-text text-area <c:if test="${!btg:contains(errors,'invalidNewsTextError' )}">error</c:if>" name="text" id="edit-text" cols="0" rows="0">${text}</textarea>
    <p class="error-label" id="edit-invalid-text"
       <c:if test="${!btg:contains(errors,'invalidNewsTextError' )}">style="display:none;"</c:if>>
        <fmt:message key="admin.news.invalid_text"/>
    </p>
    <div class="btn-group">
        <input type="submit" value='<fmt:message key="common.btn.save"/>'>
        <input class="cancel-edit-news" type="button" value='<fmt:message key="common.btn.cancel"/>'>
    </div>
</form>
<div class="popup-wrap" id="delete-news-popup" style="display: none">
    <div class="popup-holder delete-news-holder" id="delete-news" style="display: none">
        <form class="delete-news-form clearfix" action="${pageContext.servletContext.contextPath}/controller"
              method="POST">
            <input type="hidden" name="command" value="delete_news"/>
            <h3><fmt:message key="admin.delete_question"/></h3>
            <input class="input-text" type="text" name="title" id="delete-news-title" value=""
                   readonly="readonly">
            <div class="btn-group">
                <input type="submit" value='<fmt:message key="common.btn.delete"/>'>
                <input class="cancel-delete-news" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close cancel-delete-news"><a href="">x</a></div>
    </div>
</div>

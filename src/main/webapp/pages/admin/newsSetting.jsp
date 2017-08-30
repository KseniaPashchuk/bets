<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<article class="row">
    <div class="news">
        <div class="colored-block">
            <p class="error-label"
               <c:if test="${(!btg:contains(errors,'createNewsError' ))}">style="display:none;"</c:if>>
                <fmt:message key="admin.news.create.error"/>
            </p>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'deleteNewsError' )}">style="display:none;"</c:if>>
                <fmt:message key="admin.news.delete.error"/>
            </p>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'showNewsError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.news.piece.error"/>
            </p>
        </div>
        <div class="create-news-wrap colored-block">
            <div class="create-news-title"><fmt:message key="admin.news.create"/></div>
            <form class="create-news"
                  <c:if test="${(!btg:contains(errors,'invalidNewsTitleError' ))
               &&(!btg:contains(errors,'invalidNewsTextError' ))}">style="display:none;"</c:if>
                  action="${pageContext.servletContext.contextPath}/controller"
                  method="POST" enctype="multipart/form-data" onsubmit="return validateCreateNewsForm()">
                <input type="hidden" name="command" value="create_news"/>
                <input class="input-text <c:if test="${!btg:contains(errors,'invalidNewsTitleError' )}">error</c:if>"
                       type="text" name="title" value=""
                       placeholder="<fmt:message key="admin.news.create.title"/>" id="create-title">
                <p class="error-label show-message" id="create-invalid-title"
                   <c:if test="${!btg:contains(errors,'invalidNewsTitleError' )}">style="display:none;"</c:if>>
                    <fmt:message key="admin.news.invalid_title"/>
                </p>
                <label class="file_upload">
                    <span class="button"><fmt:message key="common.btn.choose"/></span>
                    <mark id="news-pic">Choose picture</mark>
                    <input type="file" name="picture" id="news_picture">
                </label>
                <textarea
                        class="input-text text-area <c:if test="${!btg:contains(errors,'invalidNewsTextError' )}">error</c:if>"
                        name="text" cols="0" rows="0"
                        onfocus="if(this.value==this.defaultValue)this.value='';"
                        onblur="if(this.value=='')this.value=this.defaultValue;" id="create-text" placeholder="<fmt:message
                        key="admin.news.create.text"/>"></textarea>
                <p class="error-label show-message" id="create-invalid-text"
                   <c:if test="${!btg:contains(errors,'invalidNewsTextError' )}">style="display:none;"</c:if>>
                    <fmt:message key="admin.news.invalid_text"/>
                </p>
                <input class="input-btn" type="submit" value="<fmt:message key="common.btn.create"/>">
            </form>
        </div>
        <input type="hidden" value="${param.date}" id="prev-date"/>
        <div class="select-news clearfix">
            <div class="select-news-title"><fmt:message key="common.news.show_news"/></div>
            <div class="select-news-date input-group date" id='select-news-date'>
                <input type="text" class="form-control"/>
                <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
            <input type="button" id="select-news-btn" class="select-news-btn"
                   value="<fmt:message key="common.btn.show"/>">
        </div>
        <div class="news-wrap" id="news-wrap">

        </div>
        <div class="colored-block show-message">
            <p class="" id="no-news" style="display:none;">
                <fmt:message key="common.news.no_news"/>
            </p>
            <p class="error-label" id="news-error" style="display:none;">
                <fmt:message key="common.error.server_error"/>
            </p>
        </div>
        <div class="news-pagination" id="pagination">
        </div>
        <div class="popup-wrap" id="delete-news-popup"
             style="display:none;">
            <div class="popup-holder delete-news-holder" id="delete-news"
                 style="display:none;">
                <form class="delete-news-form clearfix" action="${pageContext.servletContext.contextPath}/controller"
                      method="POST">
                    <input type="hidden" name="command" value="delete_news"/>
                    <h3><fmt:message key="admin.delete_question"/></h3>
                    <input class="input-text" type="text" name="title" id="delete-news-title" value=""
                           readonly="readonly">
                    <div class="btn-group">
                        <input type="submit" value='<fmt:message key="common.btn.delete"/>'>
                        <input class="delete-news-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
                    </div>
                </form>
                <div class="popup-close delete-news-close"><a href="">x</a></div>
            </div>
        </div>
    </div>
</article>
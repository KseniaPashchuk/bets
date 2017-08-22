<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<article class="row">
    <div class="news ">

        <div class=" create-news-wrap colored-block">
            <div class="create-news-title"><fmt:message key="admin.news.create"/></div>
            <form class="create-news" style="display: none"
                  action="${pageContext.servletContext.contextPath}/controller"
                  method="POST" enctype="multipart/form-data">
                <input type="hidden" name="command" value="create_news"/>
                <input class="input-text" type="text" name="title" value=""
                       placeholder="<fmt:message key="admin.news.create.title"/>">
                <label class="file_upload">
                    <span class="button"><fmt:message key="common.btn.choose"/></span>
                    <mark id="news-pic">Choose picture</mark>
                    <input type="file" name="picture" id="news_picture">
                </label>
                <textarea class="input-text text-area" name="text" cols="0" rows="0"
                          onfocus="if(this.value==this.defaultValue)this.value='';"
                          onblur="if(this.value=='')this.value=this.defaultValue;"><fmt:message key="admin.news.create.text"/></textarea>
                <input class="input-btn" type="submit" value="<fmt:message key="common.btn.create"/>">
            </form>
        </div>

        <div class="select-news clearfix">
            <div class="select-news-title"><fmt:message key="common.news.show_news"/></div>
            <div class="select-news-date input-group date" id='select-news-date'>
                <input type="text" class="form-control"/>
                <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
            <input type="button" id="select-news-btn" class="select-news-btn" value="<fmt:message key="common.btn.show"/>">
        </div>
        <div class="news-wrap" id="news-wrap">
        </div>
        <div class="news-pagination" id="pagination">
        </div>
        <div class="popup-wrap" id="delete-news-popup">
            <div class="popup-holder delete-news-holder" id="delete-news" style="display: none">
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
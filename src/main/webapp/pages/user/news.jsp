<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<article class="row">
    <div class="news ">
        <div>
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'showNewsError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.news.piece.error"/>
            </p>
        </div>
        <input type="hidden" value="${param.date}" id="prev-date"/>
        <div class="select-news colored-block clearfix">
            <div class="select-news-title"><fmt:message key="common.news.show_news"/></div>
            <div class="select-news-date input-group date" id='select-news-date'>
                <input type="text" class="form-control" />
                <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
            <input type="button" id="select-news-btn" class="select-news-btn" value="<fmt:message key="common.btn.show"/>">
        </div>
        <div class="news-wrap" id="news-wrap">
        </div>
        <div class="show-message">
            <p class="show-message" id="no-news" style="display:none">
                <fmt:message key="common.news.no_news"/>
            </p>
        </div>
        <p class="error-label" id="news-error" style="display:none">
            <fmt:message key="common.error.server_error"/>
        </p>
        <div class="news-pagination" id="pagination">
        </div>
    </div>
</article>
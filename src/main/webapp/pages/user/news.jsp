<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<article class="row">
    <div class="news ">
        <div class="select-news colored-block clearfix">
            <div class="select-news-title"> Показать новости за</div>
            <div class="select-news-date input-group date" id='select-news-date'>
                <input type="text" class="form-control"/>
                <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
            </div>
            <input type="button" id="select-news-btn" class="select-news-btn" value="Показать">
        </div>
        <div class="news-wrap" id="news-wrap">

        </div>
        <div class="news-pagination" id="pagination">
        </div>
    </div>
</article>
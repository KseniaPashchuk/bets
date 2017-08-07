<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<article class="row">
    <div class="news ">

        <div class=" create-news-wrap colored-block">
            <div class="create-news-title">Create News</div>
            <form class="create-news" style="display: none"  action="${pageContext.servletContext.contextPath}/controller"
                  method="POST" enctype="multipart/form-data">
                <input type="hidden" name="command" value="create_news"/>
                <input class="input-text" type="text" name="news_title" value="" placeholder="Title">
                <label class="file_upload">
                    <span class="button">Выбрать</span>
                    <mark id="news-pic">Choose picture</mark>
                    <input type="file" name="news_picture" id="news_picture" >
                </label>
                <textarea class="input-text text-area" name="news_text" cols="0" rows="0"
                          onfocus="if(this.value==this.defaultValue)this.value='';"
                          onblur="if(this.value=='')this.value=this.defaultValue;">Text</textarea>
                <input class="input-btn" type="submit" value="create">
            </form>
        </div>

        <div class="select-news clearfix">
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
        <div class="popup-wrap" id="delete-news-popup">
            <div class="popup-holder delete-news-holder" id="delete-news" style="display: none">
                <form class="delete-news-form clearfix" action="${pageContext.servletContext.contextPath}/controller" method="POST">
                    <input type="hidden" name="command" value="delete_news"/>
                    <h3>Вы действительно хотите удаить следующий элемент?</h3>
                    <input class="input-text" type="text" name="delete_title" id ="delete-news-title" value="" readonly="readonly">
                    <div class="btn-group">
                        <input type="submit" value='Удалить'>
                        <input class="delete-news-close" type="button" value='Отмена'>
                    </div>
                </form>
                <div class="popup-close delete-news-close"><a href="">x</a></div>
            </div>
        </div>
    </div>
</article>
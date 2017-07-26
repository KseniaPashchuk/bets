$(function () {
    $('#select-news-date').datetimepicker({
        format:'DD/MM/YYYY',
        defaultDate: new Date()
    });


    $('#select-news-btn').click(function (event) {

        var selectDate = $('#select-news-date').data("DateTimePicker").date();
        var newsDate = moment(selectDate).format("YYYY-MM-DD");
        $.ajax({
            url: "/ajax?command=show_news&date=" + newsDate,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The news dated " + newsDate + " was successfully received");
                if (data.length != 0) {
                    display(data);
                }else{}
                //TODO проверка на пустоту
            },
            error: function (e) {
                console.log("Failed to obtain news dated " + newsDate, e);
                //TODO add error message
            }
        });
    });
    $(window).on('load', function () {
        $('#select-news-btn').trigger("click");//TODO
    });
    function display(data) {

        $('#news-wrap').empty();
        $('#pagination').empty();
        $('#pagination').pagination({
            dataSource: data,
            pageSize: 9,

            callback: function (data, pagination) {
                var dataContainer = $('#news-wrap');
                var html = '';
                $.each(data, function (key, item) {
                    var date = new Date(item.date.year, item.date.month - 1, item.date.day);
                    if(key%3==0) {
                        html +=
                            '<form class="news-post" action="/controller" method="GET">' +
                            '<input type="hidden" name="command" value="show_piece_of_news"/>' +
                            '<div class = "post-pic-wrap col-lg-3">' +
                            '<img class ="post-pic" src="' + item.pictureUrl + '" alt="football">' +
                            '</div>' +
                            '<div class = "col-lg-9"> ' +
                            '<div class="post-title"><input type="submit" name="title" value="' + item.title + '"/></div></div>' +
                            '<div class="post-meta">' +
                            '<span class = "fa fa-clock-o"> <i class = "icon-time"></i>' +
                            '<time class="entry-date published">' + date.toLocaleString('de-DE') + '</time></span>' +
                            '</div>' +
                            '</div>' +
                            '</form>';
                    }if(key%3==1){}
                    if(key%3==2){}
                });
                dataContainer.html(html);
            }
        })
    }

});
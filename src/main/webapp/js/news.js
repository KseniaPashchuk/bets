$(function () {
    $('#select-news-date').datetimepicker({
        format: 'LL',
        defaultDate: new Date()
    });

    $(window).on('load', function () {
        $('#select-news-btn').trigger("click");//TODO
    });


    $('#select-news-btn').click(function (event) {

        var selectDate = $('#select-news-date').data("DateTimePicker").date();
        var newsDate = moment(selectDate).format("YYYY-MM-DD");
        $.ajax({
            url: "/ajax?date=" + newsDate,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The news dated " + newsDate + " was successfully received");
                display(data);
                //TODO проверка на пустоту
            },
            error: function (e) {
                console.log("Failed to obtain news dated " + newsDate, e);
                //TODO add error message
            }
        });
    });
    function display(data) {

        $('#news-wrap').empty();
        $('#pagination').pagination({
            dataSource: data,
            pageSize: 10,

            callback: function (data, pagination) {
                var dataContainer = $('#news-wrap');
                var html = '';
                $.each(data, function (key, item) {
                    var date = new Date(item.date.year, item.date.month - 1, item.date.day);
                    html +=
                        '<div class="news-post">' +
                        '<div class = "post-pic-wrap col-lg-3">' +
                        '<img class ="post-pic" src="' + item.picture + '" alt="football">' +
                        '</div>' +
                        '<div class = "col-lg-9"> ' +
                        '<div class="post-title"><a href="#">' + item.title + '</a></div>' +
                        '<div class="post-meta">' +
                        '<span class = "fa fa-clock-o"> <i class = "icon-time"></i>' +
                        '<time class="entry-date published">'+  date.toLocaleString('de-DE').toDateString() +'</time></span>' +
                        '</div>' +//TODO
                        '</div>' +
                        '</div>';
                });
                // date.toLocaleString('de-DE').slice(0,10)
                dataContainer.html(html);
            }
        })
    }

});
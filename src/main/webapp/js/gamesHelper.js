$(function () {

    $('#games').DataTable();

    $('a[id=matches-title]').click(function(){
        $('#league-list').slideToggle(500);
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
                $('#user-news-wrap').empty();
                $('#user-pagination').empty();
                if (data.length != 0) {
                    display(data);
                } else {
                    console.log("The news is empty");
                }
                //TODO проверка на пустоту
            },
            error: function (e) {
                console.log("Failed to obtain news dated " + newsDate, e);
                //TODO add error message
            }
        });
    });


    function display(data) {

        $('#user-pagination').pagination({
            dataSource: data,
            pageSize: 9,

            callback: function (data, pagination) {
                var dataContainer = $('#user-news-wrap');
                var html = '';
                $.each(data, function (key, item) {
                    var date = new Date(item.date.year, item.date.month - 1, item.date.day);
                    if (key % 3 == 0) {
                        html +=
                            '<div class="news-row row">';
                    }
                    html +=
                        '<form class=" col-md-4 news-item  colored-block" action="/controller" method="GET">' +
                        '<input type="hidden" name="command" value="show_piece_of_news"/>' +
                        '<a href="#"><img class="img-responsive" src="/image/news/' + item.pictureUrl + '" alt=""></a>' +
                        '<input class="news-title" type="submit" name="title" value="' + item.title + '">' +
                        '<span class="fa fa-clock-o">' +
                        '<i class="icon-time news-time-icon"></i>' +
                        '<time class="news-date entry-date published">' + date.toLocaleString('de-DE') + '</time>' +
                        '</span>' +
                        '</form>';

                    if (key % 3 == 2) {
                        html += '</div>';
                    }
                });
                dataContainer.html(html);
            }
        })
    }
});

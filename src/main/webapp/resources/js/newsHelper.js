$(function () {
    $('#select-news-date').datetimepicker({
        format: 'DD/MM/YYYY',
        // defaultDate: new Date()
    });


    $('#select-news-btn').click(function (event) {
        var newsDate;
        var selectDate = $('#select-news-date').data("DateTimePicker").date();
        var prevDate = $('#prev-date').val();
        if (selectDate == null) {
            if (prevDate == null || prevDate.trim() == '') {
                selectDate = new Date();
                $('#prev-date').val(selectDate);
                $('#select-news-date').data("DateTimePicker").date(selectDate);
                newsDate = moment(selectDate).format("DD/MM/YYYY");
            } else {
                newsDate = moment(prevDate, "DD/MM/YYYY").format("DD/MM/YYYY");
                $('#select-news-date').data("DateTimePicker").date(selectDate);
            }
            $('#select-news-date').data("DateTimePicker").date(prevDate);
        } else {
            newsDate = moment(selectDate).format("DD/MM/YYYY");
            $('#prev-date').val(selectDate);
        }
        $.ajax({
            url: "/ajax?command=show_news&date=" + newsDate,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The news dated " + newsDate + " was successfully received");
                $('#news-wrap').empty();
                $('#pagination').empty();
                if (data.length != 0) {
                    $('#no-news').hide();
                    $('#news-error').hide();
                    display(data);
                } else {
                    console.log("The news is empty");
                    $('#no-news').show();
                }
            },
            error: function (e) {
                console.log("Failed to obtain news dated " + newsDate, e);
                $('#news-error').show();
            }
        });
    });

    $('#select-news-btn').click();

    function display(data) {

        $('#pagination').pagination({
            dataSource: data,
            pageSize: 9,

            callback: function (data, pagination) {
                var dataContainer = $('#news-wrap');
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
                        '<time class="news-date entry-date published">' + moment(date).format("DD/MM/YY") + '</time>' +
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
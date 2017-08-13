$(function () {

    $('#select-news-date').datetimepicker({
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });

    $(".create-news-title").click(function (event) {
        $(".create-news").slideToggle(400);
    });

    $(".delete-news-close").click(function (event) {
        $("#delete-news-popup").hide();
        $("#delete-news").hide();
        return false;
    });

    $("body").on('click',".btn-delete-news",function(event){
        $("#delete-news-popup").show();
        $("#delete-news").show();
        $("#delete-news-title").val($(this).parents(".news-item").eq(0).find('.news-title').val());
        return false;
    });


    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#news_picture').attr('src', e.target.result);

                var filename = input.files[ 0 ].name;
                $('#news-pic').text(filename);
            };

            reader.readAsDataURL(input.files[0]);

        }
    }

    $("#news_picture").change(function(){
        readURL(this);
    });

    $('#select-news-btn').click(function (event) {
        var selectDate = $('#select-news-date').data("DateTimePicker").date();
        var newsDate = moment(selectDate).format("DD/MM/YYYY");

        $.ajax({
            url: "/ajax?command=show_news&date=" + newsDate,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The news dated " + newsDate + " was successfully received");
                $('#news-wrap').empty();
                $('#pagination').empty();
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
                        '<input type="hidden" name="title" value="'+item.title+'"/>' +
                        '<a href="#"><img class="img-responsive" src="/image/news/' + item.pictureUrl + '" alt=""></a>' +
                        '<input class="news-title" type="submit" name="title" value="' + item.title + '">' +
                        '<span class="fa fa-clock-o">' +
                        '<i class="icon-time news-time-icon"> </i>' +
                        '<time class="news-date entry-date published">' + date.toLocaleString('de-DE') + '</time>' +
                        '</span>' +
                        '<input type="button" class="btn-delete-news" value="DELETE">' +
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
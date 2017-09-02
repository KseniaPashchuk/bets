$(function () {
    $.ajax({
        url: "/ajax?command=show_support_page",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log("All mail was successufully received");
            $('.table-inbox tbody').empty();
            $('#pagination').empty();
            if (data.length != 0) {
                $('#no-news').hide();
                $('#news-error').hide();
                display(data);
            } else {
                console.log("The mail is empty");
                $('#no-mail').show();

            }
        },
        error: function (e) {
            console.log("Failed to obtain all mail ", e);
            $('#mail-error').show();
        }
    });

    function display(data) {

        $('#pagination').pagination({
            dataSource: data,
            pageSize: 15,

            callback: function (data, pagination) {
                var dataContainer = $('.table-inbox tbody');
                var html = '';
                $.each(data, function (key, item) {
                    var date = new Date(item.mailDate.date.year, item.mailDate.date.month - 1, item.mailDate.date.day,
                        item.mailDate.time.hour, item.mailDate.time.minute);
                    html +=
                        '<tr>' +
                        '<td >' +
                        '<form class="view-message" action="/controller" method="GET">' +
                        '<input type="hidden" name="command" value="show_support_chat"/>' +
                        '<input type="hidden" name="email" value="' + item.userEmail + '"/>' +
                        '<input type="submit" value="' + item.userEmail + '"/>' +
                        '</form>' +
                        '</td>' +
                        '<td class="view-message">' + item.mailText.substring(0, 15) + '</td>' +
                        '<td class="view-message inbox-small-cells"></td>' +
                        '<td class="view-message text-right">' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                        '</form>' +
                        '</tr>';

                });
                dataContainer.html(html);
            }
        })
    }

});
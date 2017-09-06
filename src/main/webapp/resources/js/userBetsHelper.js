$(document).ready(function () {
    var betMap = {"FW":"1" , "SW":"2","X":"X","FS":"12","FWX":"1X","XSW":"X2","TL":"L","TM":"M"};

    $('#user-bets-table').DataTable();

    $("#open-bets").click(function () {
        findBets('open');
    });
    $("#winned-bets").click(function () {
        findBets('winned');
    });
    $("#lost-bets").click(function () {
        findBets('lost');
    });

    function findBets(type) {
        $("#prev-type").val(type);
        $.ajax({
                url: "/ajax?command=show_bets&type=" + type,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    console.log("The bets with type " + type + " was successfully received");
                    console.log(data);
                    $('.bets-table tbody').empty();
                    if (data.length != 0) {
                        var dataContainer = $('.bets-table tbody');
                        var html = '';
                        $.each(data, function (key, item) {
                            html += '<tr>' +
                                '<td>' + item.footballMatch + '</td>' +
                                '<td>' + betMap[item.betType] + '</td>' +
                                '<td>' + item.coefficient + '</td>' +
                                '<td>' + item.money + '</td>';
                            if (type == 'winned') {
                                html += '<td>' + (item.money * item.coefficient).toFixed(2) + '</td>';
                            } else {
                                html += '<td>0</td>';
                            }
                            html += '</tr>';
                        });
                        dataContainer.html(html);
                    } else {
                        console.log("The news is empty");
                        $('#no-bets').show();
                    }

                },
                error: function (e) {
                    console.log("Failed to obtain matches", e);
                    $('#bets-error').show();
                }
            }
        );
    }
    if ($("#prev-type").val() != null && $("#prev-type").val().trim() != '') {
        findBets($("#prev-type").val());
    }

});
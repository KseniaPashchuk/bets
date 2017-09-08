$(document).ready(function () {
    $('a[id=matches-title]').click(function () {

        findMatches('all');
    });


    $('input[type="radio"]').click(function () {
        var selectedConfederacy = $(this).val();
        findMatches(selectedConfederacy);
    });

    if ($("#prev-confederation").val() != null && $("#prev-confederation").val().trim() != '') {
        findMatches($("#prev-confederation").val());
    } else {
        $('a[id=matches-title]').click();
    }

    function findMatches(confederation) {
        $("#prev-confederation").val(confederation);
        $.ajax({
            url: "/ajax?command=show_matches&confederation=" + confederation,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The matches with confederation " + confederation + " was successfully received");
                console.log(data);
                $('.matches-table tbody').empty();

                if (data.length != 0) {
                    data.sort(function (item1, item2) {
                        return item1.confederation > item2.confederation ? 1 : -1;
                    });
                    var dataContainer = $('.matches-table tbody');
                    var html = '';
                    var confederation = '';
                    $.each(data, function (key, item) {
                        var date = new Date(item.date.date.year, item.date.date.month - 1, item.date.date.day,
                            item.date.time.hour, item.date.time.minute);
                        if (item.confederation != confederation) {
                            html += '<tr>' +
                                '<td colspan="12" style="background: #ffa71b">' + item.confederation + '</td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '</tr>';
                            confederation = item.confederation;
                        }
                        if (moment().isAfter(moment(date))) {
                            html += '<tr id="' + item.id + '">' +
                                '<td>' + item.id + '</td>' +
                                '<td>' + item.firstTeam + '-' + item.secondTeam + '</td>' +
                                '<td>' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['SW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['X'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FWX'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FS'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['XSW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['TL'] + '</td>' +
                                '<td>' + item.total + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['TM'] + '</td>' +
                                '<td class="hidden">' + item.maxBet + '</td>' +
                                '</tr>';
                        } else {
                            html += '<tr id="' + item.id + '">' +
                                '<td>' + item.id + '</td>' +
                                '<td>' + item.firstTeam + '-' + item.secondTeam + '</td>' +
                                '<td>' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['SW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['X'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FWX'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['FS'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['XSW'] + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['TL'] + '</td>' +
                                '<td>' + item.total + '</td>' +
                                '<td>' + item.matchCoefficients.coefficients['TM'] + '</td>' +
                                '<td class="hidden">' + item.maxBet + '</td>' +
                                '</tr>';
                        }

                    });
                    dataContainer.html(html);
                } else {
                    console.log("The match list is empty");
                    $('#no-matches').show();
                }

            },
            error: function (e) {
                console.log("Failed to obtain matches", e);
                $('#matches-error').show();
            }
        });
    }

});


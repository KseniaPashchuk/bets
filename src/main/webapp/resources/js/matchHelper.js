$(document).ready(function () {

    var matchesTable = $('#games').DataTable();
    var betMap = {"1": "FW", "2": "SW", "X": "X", "12": "FS", "1X": "FWX", "X2": "XSW", "L": "TL", "M": "TM"};

    $('#select-results-date').datetimepicker({
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });


    $('.dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });

    $('a[id=matches-title]').click(function () {
        $(".coupon").show();
        $(".games-table").show();
        $(".results-table").hide();
        $(".search-results-block").hide();
        findMatches('all');
    });
    $('a[id=matches-title]').click();

    $('input[type="radio"]').click(function () {
        var selectedConfederacy = $(this).val();
        findMatches(selectedConfederacy);
    });

    function findMatches(confederacy) {
        $.ajax({
            url: "/ajax?command=show_matches&confederacy=" + confederacy,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The matches with confederation " + confederacy + " was successfully received");
                console.log(data);
                $('.matches-table tbody').empty();

                if (data.length != 0) {
                    data.sort(function (item1, item2) {
                        return item1.confederacy > item2.confederacy ? 1 : -1;
                    });
                    var dataContainer = $('.matches-table tbody');
                    var html = '';
                    var confederacy = '';
                    $.each(data, function (key, item) {
                        var date = new Date(item.date.date.year, item.date.date.month - 1, item.date.date.day,
                            item.date.time.hour, item.date.time.minute);
                        if (item.confederacy != confederacy) {
                            html += '<tr>' +
                                '<td colspan="12" style="background: #ffa71b">' + item.confederacy + '</td>' +
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
                            confederacy = item.confederacy;
                        }
                        html += '<tr id="' + item.id + '">' +
                            '<td>' + item.id + '</td>' +
                            '<td>' + item.firstTeam + '-' + item.secondTeam + '</td>' +
                            '<td>' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['FW'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['SW'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['X'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['FWX'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['FS'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['XSW'] + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['TL'] + '</td>' +
                            '<td>' + item.total + '</td>' +
                            '<td class="active">' + item.matchCoefficients.coefficients['TM'] + '</td>' +
                            '<td class="hidden">' + item.maxBet + '</td>' +
                            '</tr>';

                    });
                    dataContainer.html(html);
                } else {
                    console.log("The news is empty");
                }
                //TODO проверка на пустоту
            },
            error: function (e) {
                console.log("Failed to obtain matches", e);
                //TODO add error message
            }
        });
    }

    $("#match-results").click(function () {
        $(".games-table").hide();
        $(".coupon").hide();
        $(".results-table").show();
        $(".search-results-block").show();
    });

    $("#show-results").click(function () {

        var selectDate = $('#select-results-date').data("DateTimePicker").date();
        var resultsDate = moment(selectDate).format("DD/MM/YYYY");
        var confederacy = $("#results-confederations").val();
        $.ajax({
            url: "/ajax?command=show_match_results&date=" + resultsDate + "&confederacy=" + confederacy,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("The matches with confederation " + confederacy + " was successfully received");
                $('.results-table tbody').empty();
                if (data.length != 0) {
                    data.sort(function (item1, item2) {
                        return item1.confederacy > item2.confederacy ? 1 : -1;
                    });
                    var dataContainer = $('.results-table tbody');
                    var html = '';
                    var confederacy = '';
                    $.each(data, function (key, item) {
                        var date = new Date(item.date.date.year, item.date.date.month - 1, item.date.date.day,
                            item.date.time.hour, item.date.time.minute);
                        if (item.confederacy != confederacy) {
                            html += '<tr >' +
                                '<td colspan="4" style="background: #ffa71b">' + item.confederacy + '</td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '</tr>';
                            confederacy = item.confederacy;
                        }
                        html += '<tr id="' + item.id + '">' +
                            '<td>' + item.id + '</td>' +
                            '<td>' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                            '<td>' + item.firstTeam + '-' + item.secondTeam + '</td>' +
                            '<td>' + item.firstTeamScore + ' : ' + item.secondTeamScore + '</td>' +
                            '</tr>';

                    });
                    dataContainer.html(html);
                } else {
                    console.log("The news is empty");
                }
                //TODO проверка на пустоту
            },
            error: function (e) {
                console.log("Failed to obtain matches", e);
                //TODO add error message
            }
        });
    });
    $(".make-bet-close").click(function (event) {
        $("#make-bet-popup").hide();
        $("#make-bet").hide();
        return false;
    });

    $("#clean-bets-btn").click(function (event) {
        $('#games tbody').find('td.active.selected').each(function (index, el) {
            $(el).removeClass('selected');
            var elementRow = $(this).closest('tr').find('td');
            $("#make-bet-table").find('tr[id=' + $(elementRow).eq(0).text() + ']').remove();
        });
    });

    $('#games tbody').on('click', 'td', function () {
        if ($(this).hasClass('active')) {
            var betType = matchesTable.column($(this).index()).header();
            var elementRow = $(this).closest('tr').find('td');
            var an = $(this).closest('tr').find('td.active.selected').eq(0);

            if (an != null) {
                $(an).removeClass('selected');
                $("#make-bet-table").find("tr[id=" + $(elementRow).eq(0).text() + "]").remove();
            }
            if ($(an).index() != $(this).index()) {
                $(this).addClass('selected');

                $("#make-bet-table  tr:last").after('<tr id=' + $(elementRow).eq(0).text() + '>' +
                    '<input type="hidden" name = "match_id" readOnly="readOnly" value="' + $(elementRow).eq(0).text() + '">' +
                    '<input class="bet-type-input" type="hidden" name="bet_type" readOnly="readOnly" value="' + betMap[$(betType).html()] + '"/>' +
                    '<td>' + $(elementRow).eq(1).text() + '</td>' +
                    '<td>' + $(elementRow).eq(2).text() + '</td>' +
                    '<td>' + $(betType).html() + '</td>' +
                    '<td>' + $(this).html() + '</td>' +
                    '<td><input class="bet-money-input" type="text" name="bet_money" value=""/></td><td>' +
                    $(elementRow).eq(12).text() + '</td><td>' +
                    '<button class="remove-row-btn"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td></tr>');

            }
        }

    });

    $("body").on('click', ".remove-row-btn", function (event) {
        var idx = $(this).closest('tr').attr("id");
        var aaa = $("#games tbody").find('tr[id=' + idx + '] td.active.selected').removeClass('selected');
        $(this).closest('tr').remove();
        var rowCount = $('#make-bet-table tbody tr').length;
        if (rowCount < 2) {
            $(".make-bet-close").click();
        }
        return false;
    });

    $("#make-bet-btn").click(function (event) {
        var rowCount = $('#make-bet-table tbody tr').length;
        if (rowCount > 1) {
            $("#make-bet-popup").show();
            $("#make-bet").show();
        }
    });

});

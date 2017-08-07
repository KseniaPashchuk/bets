$(function () {

    var matchesTable = $('#games').DataTable();

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
                $('.matches-table tbody').empty();

                if (data.length != 0) {
                    data.sort(function (item1, item2) {
                        return item1.confederacy > item2.confederacy ? 1 : -1;
                    });
                    var dataContainer = $('.matches-table tbody');
                    var html = '';
                    var confederacy = '';
                    $.each(data, function (key, item) {
                        var date = new Date(item.date.year, item.date.month - 1, item.date.day, item.date.hour, item.date.minute, item.date.seconds);
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
                            '<td>' + date.toLocaleString('de-DE') + '</td>' +
                            '<td class="active">' + item.coefficients['FW'] + '</td>' +
                            '<td class="active">' + item.coefficients['SW'] + '</td>' +
                            '<td class="active">' + item.coefficients['X'] + '</td>' +
                            '<td class="active">' + item.coefficients['FWX'] + '</td>' +
                            '<td class="active">' + item.coefficients['FS'] + '</td>' +
                            '<td class="active">' + item.coefficients['XSW'] + '</td>' +
                            '<td class="active">' + item.coefficients['TL'] + '</td>' +
                            '<td>' + item.total + '</td>' +
                            '<td class="active">' + item.coefficients['TM'] + '</td>' +
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
        var resultsDate = moment(selectDate).format("YYYY-MM-DD");
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
                        var date = new Date(item.date.year, item.date.month - 1, item.date.day);
                        if (item.confederacy != confederacy) {
                            html += '<tr >' +
                                '<td colspan="12" style="background: white">' + item.confederacy + '</td>' +
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
                            '<td>' + date.toLocaleString('de-DE') + '</td>' +
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
            $("#make-bet-table").find('tr[id=' + matchesTable.cell(matchesTable.cell(el).index().row, 0).data() + ']').remove();
        });
    });

    $('#games tbody').on('click', 'td', function () {
        if ($(this).hasClass('active')) {
            var colIdx = matchesTable.cell(this).index().column;
            var rowIdx = matchesTable.cell(this).index().row;
            var betType = matchesTable.column(colIdx).header();
            var elementRow = matchesTable.row(this).node();
            var an = $(elementRow).find('td.active.selected').eq(0);
            if (an != null) {
                $(an).removeClass('selected');
                $("#make-bet-table").find('tr[id=' + matchesTable.cell(rowIdx, 0).data() + ']').remove();
                $('span[id = betsCount]').text(parseInt($('span[id = betsCount]').text()) - 1);
            }
            if ($(an).index() != $(this).index()) {
                $(this).addClass('selected');

                $("#make-bet-table  tr:last").after('<tr id=' + matchesTable.cell(rowIdx, 0).data() + '>' +
                    '<input type="hidden" name = "event_id" readOnly="readOnly" value="' + matchesTable.cell(rowIdx, 0).data() + '">' +
                    '<td>' + matchesTable.cell(rowIdx, 1).data() + '</td>' +
                    '<td>' + matchesTable.cell(rowIdx, 2).data() + '</td>' +
                    '<td><input class="bet-type-input" type="text" name="bet_type" readOnly="readOnly" value="' + $(betType).html() + '"/></td>' +
                    '<td>' + $(this).html() + '</td>' +
                    '<td><input class="bet-money-input" type="text" name="bet_money" value=""/></td><td>' +
                    $("td:nth-child(13)", elementRow).text() + '</td><td>' +
                    '<button class="remove-row-btn"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td></tr>');
                $('span[id = betsCount]').text(parseInt($('span[id = betsCount]').text()) + 1);
            }
        }

    });

    $("#make-bet-btn").click(function (event) {
        var rowCount = $('#make-bet-table tbody tr').length
        if (rowCount > 1) {
            $("#make-bet-popup").show();
            $("#make-bet").show();
        }
    });

});

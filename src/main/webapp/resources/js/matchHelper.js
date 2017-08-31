$(document).ready(function () {

    var matchesTable = $('#games').DataTable();
    var betMap = {"1": "FW", "2": "SW", "X": "X", "12": "FS", "1X": "FWX", "X2": "XSW", "L": "TL", "M": "TM"};


    $('.dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });

    $('a[id=matches-title]').click(function () {

        findMatches('all');
    });


    $('input[type="radio"]').click(function () {
        var selectedConfederacy = $(this).val();
        findMatches(selectedConfederacy);
    });

    if ($("#prev-confederacy").val() != null && $("#prev-confederacy").val().trim() != '') {
        findMatches($("#prev-confederacy").val());
    } else {
        $('a[id=matches-title]').click();
    }

    function findMatches(confederation) {
        $("#prev-confederacy").val(confederation);
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
                        if(moment().isAfter(moment(date))) {
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
                        }else{
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

    $("#match-results").click(function () {
        $(".games-table").hide();
        $(".coupon").hide();
        $(".results-table").show();
        $(".search-results-block").show();
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
                    '<input type="hidden" name = "matchId" readOnly="readOnly" value="' + $(elementRow).eq(0).text() + '">' +
                    '<input type="hidden" name="betType" value="' + betMap[$(betType).html()] + '"/>' +
                    '<td><input type="text" name="event" value="' + $(elementRow).eq(1).text() + '"/></td>' +
                    '<td><input class="make-bet-input"  type="text" name="date" value="' + $(elementRow).eq(2).text() + '"/></td>' +
                    '<td><input class="make-bet-input" type="text"  name="type" value="' + $(betType).html() + '"/></td>' +
                    '<td><input class="make-bet-input" type="text"  name="betCoeff" value="' + $(this).html() + '"/></td>' +
                    '<td><input class="money-input" type="text" name="money" value=""/></td>' +
                    '<td><input class="max-bet-input" type="text" name="maxBet" readOnly="readOnly" value="' + $(elementRow).eq(12).text() + '"/></td>' +
                    '<td><button class="remove-row-btn"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td></tr>');

            }
        }

    });

    $("body").on('click', ".remove-row-btn", function (event) {
        var idx = $(this).closest('tr').attr("id");
        $("#games tbody").find('tr[id=' + idx + '] td.active.selected').removeClass('selected');
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
function validateMakeBetForm() {

    $("#invalid-summ").hide();
    $("#too-big-summ").hide();

    var isFormValid = true;

    $.each($(".make-bet-table tbody tr"), function (key, item) {

        if (item.find('.money-input').eq(0).val() == null || parseInt(item.find('.money-input').eq(0).val()) < 0) {
            isFormValid = false;
            $("#invalid-summ").show();
        }
        if (item.find('.money-input').eq(0).val() == null || parseInt(item.find('.max-bet-input').eq(0).val()) < parseInt(item.find('.money-input').eq(0).val())) {
            isFormValid = false;
            $("#too-big-summ").show();
        }
    });

    return isFormValid;
}
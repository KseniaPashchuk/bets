$(document).ready(function () {
    var matchesTable = $('#games').DataTable();

    $('#create-match-date').datetimepicker({
        format: 'DD/MM/YY HH:mm',

        defaultDate: new Date()
    });

    $('#edit-match-date').datetimepicker({
        format: 'DD/MM/YY HH:mm'
    });

    $('#dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });


    $('input[type="radio"]').click(function () {
        var selectedConfederacy = $(this).val();
        findMatches(selectedConfederacy);
    });

    $(".create-block button").click(function (event) {
        $("#create-game-popup").show();
        $("#create-game").show();
    });

    $('a[id=matches-title]').click(function () {
        findMatches('all');
    });


    $("body").on('click', ".btn-edit-game", function (event) {
        var elRow = $(this).closest('tr').find('td');
        var rowIdx = $(this).closest('tr').index();
        $("input[id=edit-match-id]").val($(elRow).eq(0).text());
        var ev = $(elRow).eq(1).text();
        var firstTeam = ev.split('-')[0].trim();
        var secondTeam = ev.split('-')[1].trim();
        $("#first-team-edit").find("option:contains('" + firstTeam + "')").prop("selected", true);
        $("#second-team-edit").find("option:contains('" + secondTeam + "')").prop("selected", true);
        $("#create-match-date-input").val($(elRow).eq(2).text());
        $("#confederation-edit").find("option:contains('" + $(elRow).eq(13).text() + "')").prop("selected", true);

        for (var i = 3; i < elRow.length - 1; i++) {
            $("#edit-games-table td").eq(i - 3).find('input').val($(elRow).eq(i).text());
        }
        $("#edit-games-popup").show();
        $("#edit-games").show();

    });


    $(".edit-games-close").click(function (event) {
        $("#edit-games-popup").hide();
        $("#edit-games").hide();
        $("#edit-same-team").hide();
        $("#edit-invalid-date").hide();
        $("#edit-invalid-max-bet").hide();
        $("#edit-invalid-coeff").hide();
        return false;
    });
    $(".create-game-close").click(function (event) {
        $("#create-game-popup").hide();
        $("#create-game").hide();
        $("#create-same-team").hide();
        $("#create-invalid-date").hide();
        $("#create-invalid-max-bet").hide();
        $("#create-invalid-coeff").hide();
        return false;
    });

    $(".save-edit-game").click(function (event) {

        $("input[id=edit-first-team]").val($("#first-team-edit").val());
        $("input[id=edit-second-team]").val($("#second-team-edit").val());
        $("input[id=edit-confederation]").val($("#confederation-edit").val());
        $(".edit-games-form").submit();
    });


    $("body").on('click', ".btn-set-score", function (event) {
        var elRow = $(this).closest('tr').find('td');
        var id = $(elRow).eq(0).text();
        var date = $(elRow).eq(2).text();
        $("input[id=set-score-match-id]").val(id);
        $("input[id=set-score-match-date]").val(moment(date).format('DD/MM/YY HH:mm'));
        var ev = $(elRow).eq(1).text();
        var firstTeam = ev.split('-')[0].trim();
        var secondTeam = ev.split('-')[1].trim();
        $("#set-score-table").find('input[id=first-team]').val(firstTeam);
        $("#set-score-table").find('input[id=second-team]').val(secondTeam);
        $("#set-score-popup").show();
        $("#set-score").show();
    });


    $(".set-score-close").click(function (event) {
        $("#set-score-popup").hide();
        $("#set-score").hide();
        $("#invalid-score").hide();
        $("#invalid-date").hide();
        return false;
    });

    $(".save-create-game").click(function (event) {
        $("input[id=create-first-team]").val($("#first-team-create").val());
        $("input[id=create-second-team]").val($("#second-team-create").val());
        $("input[id=create-confederation]").val($("#confederation-create").val());
        $(".create-game-form").submit();
    });


    $('a[id=matches-title]').click(function () {
        $(".coupon").show();
        $(".games-table").show();
        $(".results-table").hide();
        $(".search-results-block").hide();
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
                                '<td colspan="14" style="background: #ffa71b">' + item.confederation + '</td>' +
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
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '</tr>';
                            confederation = item.confederation;
                        }
                        html += '<tr id="' + item.id + '">' +
                            '<td>' + item.id + '</td>' +
                            '<td>' + item.firstTeam + ' - ' + item.secondTeam + '</td>' +
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
                            '<td class="hidden">' + item.confederation + '</td>' +
                            '<td class="btn-ctrl"><button class="btn btn-primary btn-xs btn-edit-game">' +
                            '<span class="glyphicon glyphicon-pencil"></span></button></td>' +
                            '<td class="btn-ctrl"><button class="btn btn-primary btn-xs btn-set-score">' +
                            '<i class="fa fa-futbol-o" aria-hidden="true"></i></button></td>' +
                            '</tr>';

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
function validateCreateMatchForm() {
    $("#create-same-team").hide();
    $("#first-team-create").css('border', 'transparent');
    $("#second-team-create").css('border', 'transparent');
    $("#create-invalid-date").hide();
    $("#create-match-date").css('border', 'transparent');
    $("#create-invalid-coeff").hide();
    $.each($(".create-game-form.coeff-input"), function (key, item) {
            item.css('border', 'transparent');
    });
    $("#create-invalid-max-bet").hide();
    $("#max-bet-create").css('border', 'transparent');

    var isFormValid = true;

    if ($("#first-team-create").val().localeCompare($("#second-team-create").val()) == 0) {
        isFormValid = false;
        $("#create-same-team").show();
        $("#first-team-create").css('border', 'solid 2px maroon');
        $("#second-team-create").css('border', 'solid 2px maroon');
    }

    var matchDate = $('#create-match-date').data("DateTimePicker").date();

    if (moment().isAfter(matchDate)) {
        isFormValid = false;
        $("#create-invalid-date").show();
        $("#create-match-date").css('border', 'solid 2px maroon');
    }

    $.each($(".create-game-form.coeff-input"), function (key, item) {
        if (item.val() == null || parseFloat(item.val()) <= 0.00) {
            isFormValid = false;
            $("#create-invalid-coeff").show();
            item.css('border', 'solid 2px maroon');
        }
    });

    if ($(".edit-games-form").find("input[name=maxBet]").val() == null || parseFloat($(".edit-games-form").find("input[name=maxBet]").val()) <= 0.00) {
        isFormValid = false;
        $("#create-invalid-max-bet").show();
        $(".edit-games-form").find("input[name=maxBet]").css('border', 'solid 2px maroon');
    }

    return isFormValid;
}
function validateEditMatchForm() {

    $("#edit-same-team").hide();
    $("#first-team-edit").css('border', 'transparent');
    $("#second-team-edit").css('border', 'transparent');
    $("#edit-invalid-date").hide();
    $("#edit-match-date").css('border', 'transparent');
    $("#edit-invalid-coeff").hide();
    $.each($(".edit-games-form.coeff-input"), function (key, item) {
        item.css('border', 'transparent');
    });
    $("#edit-invalid-max-bet").hide();
    $("#max-bet-edit").css('border', 'transparent');

    var isFormValid = true;

    if ($("#first-team-edit").val().localeCompare($("#second-team-edit").val()) == 0) {
        isFormValid = false;
        $("#edit-same-team").show();
        $("#first-team-edit").css('border', 'solid 2px maroon');
        $("#second-team-edit").css('border', 'solid 2px maroon');
    }

    var matchDate = $('#edit-match-date').data("DateTimePicker").date();

    if (moment().isAfter(matchDate)) {
        isFormValid = false;
        $("#edit-invalid-date").show();
        $("#edit-match-date").css('border', 'solid 2px maroon');
    }

    $.each($(".edit-games-form.coeff-input"), function (key, item) {
        if (item.val() == null || parseFloat(item.val()) <= 0.00) {
            isFormValid = false;
            $("#edit-invalid-coeff").show();
            item.css('border', 'solid 2px maroon');
        }
    });

    if ($(".edit-games-form").find("input[name=maxBet]").val() == null || parseFloat($(".edit-games-form").find("input[name=maxBet]").val()) <= 0.00) {
        isFormValid = false;
        $("#edit-invalid-max-bet").show();
        $(".edit-games-form").find("input[name=maxBet]").css('border', 'solid 2px maroon');
    }

    return isFormValid;
}
function validateSetScoreForm() {

    $("#invalid-score").hide();
    $("#first-team-score").css('border', 'transparent');
    $("#second-team-score").css('border', 'transparent');

    var isFormValid = true;
    if ($("#first-team-score").val() == null ||
        $("#second-team-score").val() == null ||
        parseInt($("#first-team-score").val()) < 0 ||
        parseInt($("#second-team-score").val()) < 0) {
        isFormValid = false;
        $("#invalid-score").show();
        $("#first-team-score").css('border', 'solid 2px maroon');
        $("#second-team-score").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}
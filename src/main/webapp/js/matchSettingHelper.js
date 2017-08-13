$(document).ready(function () {
    var matchesTable = $('#games').DataTable();


    $('#select-results-date').datetimepicker({
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });

    $('#create-match-date').datetimepicker({
        format: 'DD/MM/YYYY HH:mm',
        defaultDate: new Date()
    });

    $('#edit-match-date').datetimepicker({
        format: 'DD/MM/YYYY HH:mm'
    });

    $('.dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });


    $('input[type="radio"]').click(function () {
        alert($(this).val());
    });

    $(".create-block button").click(function (event) {
        $("#create-game-popup").show();
        $("#create-game").show();
    });
    $("#match-results").click(function () {
        $(".games-table").hide();
        $(".coupon").hide();
        $(".results-table").show();
        $(".search-results-block").show();
    });
    $('a[id=matches-title]').click(function () {
        $(".coupon").show();
        $(".games-table").show();
        $(".results-table").hide();
        $(".search-results-block").hide();
    });


    $("body").on('click', ".btn-edit-game", function (event) {
        var elRow = $(this).closest('tr').find('td');
        var rowIdx = $(this).closest('tr').index();
        alert(rowIdx);
        $("input[id=edit-match-id]").val($(elRow).eq(0).text());
        var ev = $(elRow).eq(1).text();
        var firstTeam = ev.split('-')[0].trim();
        var secondTeam = ev.split('-')[1].trim();
        $("#first-team-select option:contains('" + firstTeam + "')").prop("selected", true);
        $("#second-team-select option:contains('" + secondTeam + "')").prop("selected", true);
        $("#create-match-date-input").val($(elRow).eq(2).text());
        $("#confederacy-select option:contains('" + $(elRow).eq(13).text() + "')").prop("selected", true);

        for (var i = 3; i < elRow.length - 1; i++) {
            $("#edit-games-table td").eq(i - 3).find('input').val($(elRow).eq(i).text());
        }
        $("#edit-games-popup").show();
        $("#edit-games").show();

    });


    $(".edit-games-close").click(function (event) {
        $("#edit-games-popup").hide();
        $("#edit-games").hide();
        return false;
    });
    $(".create-game-close").click(function (event) {
        $("#create-game-popup").hide();
        $("#create-game").hide();
        return false;
    });

    $(".save-edit-game").click(function (event) {

        $("input[id=edit-first-team]").val($("#first-team-select").val());
        $("input[id=edit-second-team]").val($("#second-team-select").val());
        $("input[id=edit-confederacy]").val($("#confederacy-select").val());
        $(".edit-games-form").submit();
    });


    $("body").on('click', ".btn-set-score", function (event) {
        var elRow = $(this).closest('tr').find('td');
        var id = $(elRow).eq(0).text();
        $("input[id=set-score-match-id]").val(id);
        var ev = $(elRow).eq(1).text();
        var firstTeam = ev.split('-')[0].trim();
        var secondTeam = ev.split('-')[1].trim();
        $("#set-score-table").find('td[id=first-team]').text(firstTeam);
        $("#set-score-table").find('td[id=second-team]').text(secondTeam);
        $("#set-score-popup").show();
        $("#set-score").show();
    });


    $(".set-score-close").click(function (event) {
        $("#set-score-popup").hide();
        $("#set-score").hide();
        return false;
    });

    $(".save-create-game").click(function (event) {
        $("input[id=create-first-team]").val($("#first-team-create").val());
        $("input[id=create-second-team]").val($("#second-team-create").val());
        $("input[id=create-confederacy]").val($("#confederacy-create").val());
        $(".create-game-form").submit();
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
                        var date = new Date(item.date.date.year, item.date.date.month - 1, item.date.date.day,
                            item.date.time.hour, item.date.time.minute);
                        if (item.confederacy != confederacy) {
                            html += '<tr>' +
                                '<td colspan="14" style="background: #ffa71b">' + item.confederacy + '</td>' +
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
                            confederacy = item.confederacy;
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
                            '<td class="hidden">' + item.confederacy + '</td>' +
                            '<td class="btn-ctrl"><button class="btn btn-primary btn-xs btn-edit-game">' +
                            '<span class="glyphicon glyphicon-pencil"></span></button></td>' +
                            '<td class="btn-ctrl"><button class="btn btn-primary btn-xs btn-set-score">' +
                            '<i class="fa fa-futbol-o" aria-hidden="true"></i></button></td>' +
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
                            html += '<tr>' +
                                '<td colspan="12" style="background: #ffa71b">' + item.confederacy + '</td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '<td style="display: none;"></td>' +
                                '</tr>';
                            confederacy = item.confederacy;
                        }
                        html += '<tr id="' + item.id + '">' +
                            '<td>' + item.id + '</td>' +
                            '<td>' + moment(date).format("DD/MM/YY HH:mm") + '</td>' +
                            '<td>' + item.firstTeam + ' - ' + item.secondTeam + '</td>' +
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


});
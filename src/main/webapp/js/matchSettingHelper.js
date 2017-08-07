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
        var rowIdx = $(this).closest('tr').index();
        var id = matchesTable.cell(rowIdx + 1, 0).data();
        $("input[id=set-score-match-id]").val(id);
        var ev = matchesTable.cell(rowIdx + 1, 1).data();
        var fisrtTeam = ev.split('-')[0];
        var secondTeam = ev.split('-')[1];
        $("#set-score-table").find('td[id=first-team]').text(fisrtTeam);
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
                            '<td>' + formatDate(date) + '</td>' +
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

    function formatDate(date) {
        var day = date.getDate();
        var month = date.getMonth();
        var year = date.getFullYear();
        var hour = date.getHours();
        var minute = date.getMinutes();
        return day + '/' + month + '/' + year + ' ' +hour + ':' +minute;
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
                            html += '<tr>' +
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
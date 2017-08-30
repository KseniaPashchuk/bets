$(document).ready(function () {

    var results = $('#results').DataTable();

    $('#select-results-date').datetimepicker({
        format: 'DD/MM/YYYY'
        // defaultDate: new Date()
    });


    $('.dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });

    $('a[id=matches-title]').click(function () {
        $("#confederacy").val('all');
        $("#show-matches-page").submit();
    });

    $('input[type="radio"]').click(function () {
        var selectedConfederacy = $(this).val();
        $("#confederacy").val(selectedConfederacy);
        $("#show-matches-page").submit();
    });

    $("#show-results").click(function () {
        var resultsDate;
        var selectDate = $('#select-results-date').data("DateTimePicker").date();
        var prevDate = $('#prev-date').val();
        if (selectDate == null) {
            if (prevDate == null || prevDate.trim() == '') {
                selectDate = new Date();
                $('#prev-date').val(selectDate);
                $('#select-results-date').data("DateTimePicker").date(selectDate);
                resultsDate = moment(selectDate).format("DD/MM/YYYY");
            } else {
                resultsDate = moment(prevDate, "DD/MM/YYYY").format("DD/MM/YYYY");
                $('#select-results-date').data("DateTimePicker").date(selectDate);
            }
            $('#select-results-date').data("DateTimePicker").date(prevDate);
        } else {
            resultsDate = moment(selectDate).format("DD/MM/YYYY");
            $('#prev-date').val(selectDate);
        }

        var isConfederacyValid = true;

        var confederacy;
        var selectConfederacy = $("#results-confederations").val();
        var prevConfederacy = $("#prev-confederacy").val();
        if (selectConfederacy != null && selectConfederacy.trim() !='') {
            confederacy = selectConfederacy;
            $('#prev-confederacy').val(selectConfederacy);
        } else {
            if (prevConfederacy != null || prevConfederacy.trim() != '') {
                confederacy = prevConfederacy;
                $("#results-confederations").val(prevConfederacy);
            } else {
                isConfederacyValid = false;
            }
        }

        if (isConfederacyValid) {
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
                        $('#no-results').show();
                        console.log("The results is empty");
                    }

                },
                error: function (e) {
                    console.log("Failed to obtain match results", e);
                    $('#results-error').show();
                }
            });
        }
        else {
            $("#results-confederations").css('border', 'solid 2px maroon');
        }
    });

    if ($("#prev-date").val != null || $("#prev-date").val.trim() != '') {
        $("#show-results").click();
    } else {
        $('#select-results-date').data("DateTimePicker").date(new Date());
    }
});
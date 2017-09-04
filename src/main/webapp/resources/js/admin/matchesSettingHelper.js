$(document).ready(function() {

    $('#dropdown').click(function () {
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
});

function validateCreateConfederationForm() {

    $("#invalid-confederation").hide();
    $("#confederation-name").css('border', 'transparent');

    var isFormValid = true;
    if ($("#confederation-name").val().trim() == '') {
        isFormValid = false;
        $("#invalid-confederation").show();
        $("#confederation-name").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}

function validateCreateTeamForm() {

    $("#invalid-team-name").hide();
    $("#team-name").css('border', 'transparent');
    $("#invalid-country").hide();
    $("#country").css('border', 'transparent');
    var isFormValid = true;
    if ($("#team-name").val().trim() == '') {
        isFormValid = false;
        $("#invalid-team-name").show();
        $("#team-name").css('border', 'solid 2px maroon');
    }
    if ($("#country").val().trim() == '') {
        isFormValid = false;
        $("#invalid-country").show();
        $("#country").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}

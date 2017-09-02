
$(document).ready(function() {
    $('.dropdown').click(function () {
        $('#league-list').slideToggle(500);
    });
});

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

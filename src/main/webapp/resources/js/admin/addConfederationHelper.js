function validateCreateConfedeartionForm() {

    $("#invalid-confederation").hide();
    $("#confederation").css('border', 'transparent');

    var isFormValid = true;
    if ($("#confederation").val().trim() == '') {
        isFormValid = false;
        $("#invalid-confederation").show();
        $("#confederation").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}


$(function () {

    $(".btn-edit").click(function(event) {
        $("#news-post").hide();
        $("#edit-news").show();
        return false;
    });
    $(".cancel-edit-news").click(function(event) {
        $("#news-post").show();
        $("#edit-news").hide();
        return false;
    });
    $(".cancel-delete-news").click(function(event) {
        $("#delete-news-popup").hide();
        $("#delete-news").hide();
        return false;
    });

    $(".btn-delete").click(function(event) {
        $("#delete-news-popup").show();
        $("#delete-news").show();
        $("#delete-news-title").val($(this).parents(".post-info").eq(0).find('.post-title').text());
        return false;
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#news-picture').attr('src', e.target.result);
                $('#news-pic').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#news-picture").change(function () {
        readURL(this);
    });
});
function validateEditNewsForm(){

    $("#edit-invalid-title").hide();
    $("#edit-title").css('border', 'transparent');
    $("#edit-invalid-text").hide();
    $("#edit-text").css('border', 'transparent');

    var isFormValid = true;
    if ($("#edit-title").val().trim() == '') {
        isFormValid = false;
        $("#edit-invalid-title").show();
        $("#edit-title").css('border', 'solid 2px maroon');
    }
    if ($("#edit-text").val().trim() == '') {
        isFormValid = false;
        $("#edit-invalid-text").show();
        $("#edit-text").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}

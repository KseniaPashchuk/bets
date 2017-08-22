$(document).ready(function() {

    $('.faq-item-title').click(function(){
        $('#faq_answer_' + this.id.substring(10, this.id.length)).slideToggle(300);
    });

    $(".create-faq-title").click(function(event) {
        $(".create-faq").slideToggle(400);
    });
    $(".edit-faq-close").click(function(event) {
        $("#edit-faq-popup").hide();
        $("#edit-faq").hide();

        return false;
    });
    $(".delete-faq-close").click(function(event) {
        $("#delete-faq-popup").hide();
        $("#delete-faq").hide();

        return false;
    });

    $(".btn-delete").click(function(event) {
        $("#delete-faq-popup").show();
        $("#delete-faq").show();
        $("#delete-faq-question").val($(this).parents(".faq-item").eq(0).find('.faq-item-title').text());
        return false;
    });

    $(".btn-edit").click(function(event) {
        $("#edit-faq-popup").show();
        $("#edit-faq").show();
        $("#edit-question").val($(this).parents(".faq-item").eq(0).find('.faq-item-title').text());
        $("#edit-answer").val($(this).parents(".faq-item").eq(0).find('.faq-item-answer').text());
        return false;
    });
});

function validateCreateFaqForm() {
    var isFormValid = true;
    if ($("#create-question").val().trim() == '') {
        isFormValid = false;
        $("#create-invalid-question").innerHTML('<fmt:message key="admin.faq.create.invalid_question"/>');
    }
    if ($("#create-answer").val().trim() == '') {
        isFormValid = false;
        $("#create-invalid-answer").innerHTML('<fmt:message key="admin.faq.create.invalid_answer"/>');
    }
    return isFormValid;
}
function validateEditFaqForm() {
    var isFormValid = true;
    if ($("#edit-answer").val().trim() == '') {
        isFormValid = false;
        $("#edit-invalid-answer").innerHTML('<fmt:message key="admin.faq.create.invalid_answer"/>');
    }
    return isFormValid;
}

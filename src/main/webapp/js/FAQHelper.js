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
        $("#edit-faq-question").val($(this).parents(".faq-item").eq(0).find('.faq-item-title').text());
        $("#edit-faq-answer").val($(this).parents(".faq-item").eq(0).find('.faq-item-answer').text());
        return false;
    });

} );
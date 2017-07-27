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
});
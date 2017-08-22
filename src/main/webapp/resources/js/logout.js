$(document).ready(function () {
    $(".logout-close").click(function (event) {
        $("#logout-popup").hide();
        $("#logout-check").hide();

    });
    $("#show-logout").click(function (event) {
        $("#logout-popup").show();
        $("#logout-check").show();

    });
});

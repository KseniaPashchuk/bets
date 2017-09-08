$(document).ready(function () {
    $('#select-birth-date').datetimepicker({
        format: 'DD/MM/YYYY'
    });

    $('input').focus(function () {
        $('#' + this.id + '_label').hide();
    });

    $('input').blur(function () {
        if ($(this).val().trim() === '') {
            $('#' + this.id + '_label').show();
        }
    });

    $("#forget-password").click(function (event) {
        $("#pass-recovery").show();
        $("#forget-form").show();
    });
    $(".forget-pass-close").click(function (event) {
        $("#pass-recovery").hide();
        $("#forget-form").hide();
    });

    $(".btn-enter").click(function (event) {
        $("#enter-wrap").show();
        $("#register-wrap").hide();
        $("#enter-btn")
        $("#enter-btn")
    });
    $(".btn-registration").click(function (event) {
        $("#enter-wrap").hide();
        $("#register-wrap").show();
        $("#enter-btn")
        $("#enter-btn")
    });

});
function validateEnterForm() {
    $("#invalid-params").hide();
    $("#login").css('border', 'transparent');
    $("#password").css('border', 'transparent');
    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");
    var isFormValid = true;

    if (!REGEX_EMAIL.test($("#login").val())) {
        isFormValid = false;
        $("#invalid-params").show();
        $("#login").css('border', 'solid 2px maroon');
        $("#password").css('border', 'solid 2px maroon');
    }
    if (!REGEX_PASSWORD.test($("#password").val())) {
        isFormValid = false;
        $("#invalid-params").show();
        $("#login").css('border', 'solid 2px maroon');
        $("#password").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}

function validateRegisterForm() {
    $("#invalid-login").hide();
    $("#invalid-password").hide();
    $("#invalid-conf-password").hide();
    $("#invalid-first-name").hide();
    $("#invalid-last-name").hide();
    $("#invalid-credit-card").hide();
    $("#invalid-birth-date").hide();
    $("#rule_error").hide();
    $("#reg_email").css('border', 'transparent');
    $("#reg_password").css('border', 'transparent');
    $("#conf_password").css('border', 'transparent');
    $("#reg_name").css('border', 'transparent');
    $("#reg_surname").css('border', 'transparent');
    $("#reg_credit_card").css('border', 'transparent');
    $("#select-birth-date").css('border', 'transparent');

    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");
    var REGEX_CREDIT_CARD = new RegExp("^[0-9]{13,16}$");
    var dateFrom = moment([1900, 0, 1]);

    var isFormValid = true;
    if (!REGEX_EMAIL.test($("#reg_email").val())) {
        isFormValid = false;
        $("#invalid-login").show();
        $("#reg_email").css('border', 'solid 2px maroon');
    }
    if (!REGEX_PASSWORD.test($("#reg_password").val())) {
        isFormValid = false;
        $("#invalid-password").show();
        $("#reg_password").css('border', 'solid 2px maroon');
    }

    if ($("#reg_password").val().localeCompare($("#conf_password").val()) != 0) {
        isFormValid = false;
        $("#invalid-conf-password").show();
        $("#conf_password").css('border', 'solid 2px maroon');
    }

    if ($("#reg_name").val().trim() == '') {
        isFormValid = false;
        $("#invalid-first-name").show();
        $("#reg_name").css('border', 'solid 2px maroon');
    }
    if ($("#reg_surname").val().trim() == '') {
        isFormValid = false;
        $("#invalid-last-name").show();
        $("#reg_surname").css('border', 'solid 2px maroon');
    }
    if (!REGEX_CREDIT_CARD.test($("#reg_credit_card").val())) {
        isFormValid = false;
        $("#invalid-credit-card").show();
        $("#reg_credit_card").css('border', 'solid 2px maroon');
    }
    var birthDate = $('#select-birth-date').data("DateTimePicker").date();

    if (birthDate == null || (new Date()).getFullYear() - birthDate.year() < 18) {
        isFormValid = false;
        $("#invalid-birth-date").show();
        $("#select-birth-date").css('border', 'solid 2px maroon');
    }
    var momentDate = moment(birthDate);
    if (!momentDate.isAfter(dateFrom)) {
        isFormValid = false;
        $("#invalid-birth-date").show();
        $("#select-birth-date").css('border', 'solid 2px maroon');
    }

    if (!$("#rule").is(':checked')) {
        isFormValid = false;
        $("#rule_error").show();
    }

    return isFormValid;
}
function validateRecoverPasswordForm() {

    $("#invalid-email").hide();
    $("#pass-recover-email").css('border', 'transparent');
    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var isFormValid = true;
    if (!REGEX_EMAIL.test($("#pass-recover-email").val())) {
        isFormValid = false;
        $("#invalid-email").show();
        $("#pass-recover-email").css('border', 'solid 2px maroon');
    }
    return isFormValid;
}
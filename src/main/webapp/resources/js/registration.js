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
    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");
    var isFormValid = true;

    if (!REGEX_EMAIL.test($("#login").val())) {
        isFormValid = false;
        $("#invalid-params").innerHTML = '<fmt:message key="login.error.invalid_params"/>';
    }
    if (!REGEX_PASSWORD.test($("#password").val())) {
        isFormValid = false;
        $("#invalid-params").innerHTML = '<fmt:message key="login.error.invalid_params"/>';
    }
    return isFormValid;
}

function validateRegisterForm() {

    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");
    var isFormValid = true;
    if (!REGEX_EMAIL.test($("#reg_email").val())) {
        isFormValid = false;
        $("#invalid-login").html('<fmt:message key="signup.error.invalid_login"/>');
    }
    if (!REGEX_PASSWORD.test($("#reg_password").val())) {
        isFormValid = false;
        $("#invalid-password").html('<fmt:message key="signup.error.invalid_password"/>');
    }

    if ($("#reg_password").val().localeCompare($("#conf_password").val()) != 0) {
        isFormValid = false;
        $("#invalid-conf-password").html('<fmt:message key="signup.error.match_password"/>');
    }

    if ($("#reg_name").val().trim() == '') {
        isFormValid = false;
        $("#invalid-first-name").html('<fmt:message key="signup.error.invalid_firstName"/>');
    }
    if ($("#reg_surname").val().trim() == '') {
        isFormValid = false;
        $("#invalid-last-name").html('<fmt:message key="signup.error.invalid_lastName"/>');
    }
    if ($("#reg_credit_card").val().trim() == '') {
        isFormValid = false;
        $("#invalid-credit-card").html('<fmt:message key="signup.error.invalid_credit_card"/>');
    }
    var birthDate = $('#select-birth-date').data("DateTimePicker").date();

    if (Date.now() - birthDate < 18) {
        isFormValid = false;
        $("#invalid-birth-date").html('<fmt:message key="signup.error.invalid_birth_date"/>');
    }

    if (!$("#rule").checked()) {
        isFormValid = false;
        $("#rule_error").html('<fmt:message key="signup.error.read_rules"/>');
    }

    return isFormValid;
}
function validateRegisterForm() {
    var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
    var isFormValid = true;
    if (!REGEX_EMAIL.test($("#pass-recover-email").val())) {
        isFormValid = false;
        $("#invalid-email").html('<fmt:message key="signup.error.invalid_login"/>');
    }
    return isFormValid;
}
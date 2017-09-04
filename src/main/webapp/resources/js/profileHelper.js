$(function () {
    $('#edit-birth-date').datetimepicker({
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#avatar').attr('src', e.target.result);
                $('#avatar-pic').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#avatar").change(function () {
        readURL(this);
    });

    $("#change-user-meta").click(function (event) {
        $("#user-profile").hide();
        $("#refill-user-cash").hide();
        $("#edit-user-info").show();
        return false;
    });

    $("#add-credit-card-btn").click(function (event) {
        if ($('.credit-card.active').length < 3) {
            $('.credit-card.hidden:first').removeClass('hidden').addClass('active');
        }
        return false;
    });

    $(".delete-card-btn").click(function (event) {
        if ($('.credit-card.active').length > 1) {
            $('#' + this.id.substring(7, this.id.length)).val('');
            $('#' + this.id.substring(7, this.id.length)).prop('disabled', false);
            $('#credit_card_' + this.id.substring(7, this.id.length)).removeClass('active').addClass('hidden');
        }
        return false;
    });
});

function validateChangePasswordForm() {
    $("#invalid-current-password").hide();
    $("#invalid-new-password").hide();
    $("#invalid-conf-password").hide();
    $("#current-password").css('border','transparent');
    $("#new-password").css('border','transparent');
    $("#confirm-password").css('border','transparent');

    var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");
    var isFormValid = true;
    if (!REGEX_PASSWORD.test($("#current-password").val())) {
        isFormValid = false;
        $("#invalid-current-password").show();
        $("#current-password").css('border','solid 2px maroon');
    }
    if (!REGEX_PASSWORD.test($("#new-password").val())) {
        isFormValid = false;
        $("#invalid-new-password").show();
        $("#new-password").css('border','solid 2px maroon');
    }

    if ($("#new-password").val().localeCompare($("#confirm-password").val()) != 0) {
        isFormValid = false;
        $("#invalid-conf-password").show();
        $("#confirm-password").css('border','solid 2px maroon');
    }
    return isFormValid;
}
function validateEditProfileForm(){
    $("#invalid-first-name").hide();
    $("#invalid-last-name").hide();
    $.each($('.credit-card.active'), function (key, item) {
            item.find('input').eq(0).css('border','transparent');
        item.find('input').eq(0).css('border','transparent');
    });
    $("#invalid-birth-date").hide();
    $("#edit-name").css('border','transparent');
    $("#edit-surname").css('border','transparent');
    $("#edit-birth-date").css('border','transparent');

    var isFormValid = true;
    var REGEX_CREDIT_CARD = new RegExp("^[0-9]{13,16}$");

    if ($("#edit-name").val().trim() == '') {
        isFormValid = false;
        $("#invalid-first-name").show();
        $("#edit-name").css('border','solid 2px maroon');
    }
    if ($("#edit-surname").val().trim() == '') {
        isFormValid = false;
        $("#invalid-last-name").show();
        $("#edit-surname").css('border','solid 2px maroon');
    }

var creditCards = $('.credit-card.active');
   $.each(creditCards, function (key, item) {
       if(!REGEX_CREDIT_CARD.test(item.find('input').eq(0).val().trim())){
           isFormValid = false;
           item.find('input').eq(0).css('border','solid 2px maroon');
       }
   });
    var birthDate = $('#edit-birth-date').data("DateTimePicker").date();

    if (Date.now().getFullYear() - birthDate.getFullYear() < 18) {
        isFormValid = false;
        $("#invalid-birth-date").show();
        $("#edit-birth-date").css('border','solid 2px maroon');
    }
    return isFormValid;
}
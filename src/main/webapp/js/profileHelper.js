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


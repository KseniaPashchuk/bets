$(document).ready(function() {
    $('#select-birth-date').datetimepicker({
        format: 'LL'
    });

    $('input').focus(function () {
        $('#' + this.id + '_label').hide();
    });

    $('input').blur(function () {
        if ($(this).val().trim() === '') {
            $('#' + this.id + '_label').show();
        }
    });
} );

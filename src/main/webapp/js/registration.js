$(document).ready(function() {
    $('#select-birth-date').datetimepicker({
        format:'DD/MM/YYYY'
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

$(function () {
    $("#add-credit-card-btn").click(function(event) {
        if( $('.credit-card.active').length < 3){
            $('.credit-card.hidden:first').removeClass('hidden').addClass('active');
        }
        return false;
    });

    $(".delete-card-btn").click(function(event) {
        if( $('.credit-card.active').length > 1){
            $('#' + this.id.substring(7, this.id.length)).val('');
            $('#' + this.id.substring(7, this.id.length)).prop('disabled', false);
            $('#credit_card_' +this.id.substring(7, this.id.length)).removeClass('active').addClass('hidden');
        }
        return false;
    });
});

$(document).ready(function() {

    $('.faq-item-title').click(function(){
        $('#faq_answer_' + this.id.substring(10, this.id.length)).slideToggle(500);
    });
} );
$(document).ready(function() {
	$('#games').DataTable();

	$('a[id=matches-title]').click(function(){
		$('#league-list').slideToggle(500);
	});

} );
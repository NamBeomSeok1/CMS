/*		
	date : 2022-02-22
	author :  yangHyo	
	site : 딥레이서순위
*/

$(window).on('load', function () {
	function loop() {
		$('.rank-list li').each(function (i) {
			$(this).delay((i++) * 300)
				.animate({ 'top': '5%', 'opacity': '0' }, 300)
				.animate({ 'top': '0', 'opacity': '1' }, 300);
		});
	}
	loop();
	
	setInterval(function () {
		loop();
	}, 10000);
});
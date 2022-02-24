$(document).ready(function(){
	showCartCnt();
})
	
	function showCartCnt(){
		$.ajax({
			url:'/shop/goods/selectCartList.json',
			type:'GET',
			success:function(result){
				if(result.success){
					$('#cartCnt').remove();
					$('#cart').append('<span id="cartCnt" class="label-notice">'+result.data.totCartCnt+'</span>');
				}else{
					return false;
				}
			}
		})
	}
	

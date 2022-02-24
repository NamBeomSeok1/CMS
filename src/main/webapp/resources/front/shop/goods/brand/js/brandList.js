(function() {
	
	$(document).ready(function(){
		loadBrandData();
	})

	loadBrandData = function() {
		
		$.ajax({
			url:CTX_ROOT + '/shop/goods/brandList.json',
			//data:dataJson,
			dataType:'json',
			cache:false,
			type:'get',
			success:function(json){
				//console.log(json.data);
				createBrandHTML(json.data);
			}
		});

	}
	
	createBrandHTML = function(dataArr) {
		var html = '';
		var brandIds = Object.keys(dataArr);
		brandIds.splice(brandIds.indexOf('brandEtc'), 1);
		brandIds.sort();
		brandIds.push('brandEtc');
		var dict_char = {
			'brand01' : 'ㄱ'
			, 'brand02' : 'ㄴ'
			, 'brand03' : 'ㄷ'
			, 'brand04' : 'ㄹ'
			, 'brand05' : 'ㅁ'
			, 'brand06' : 'ㅂ'
			, 'brand07' : 'ㅅ'
			, 'brand08' : 'ㅇ'
			, 'brand09' : 'ㅈ'
			, 'brand10' : 'ㅊ'
			, 'brand11' : 'ㅋ'
			, 'brand12' : 'ㅌ'
			, 'brand13' : 'ㅍ'
			, 'brand14' : 'ㅎ'
			, 'brandA' : 'A'
			, 'brandB' : 'B'
			, 'brandC' : 'C'
			, 'brandD' : 'D'
			, 'brandE' : 'E'
			, 'brandF' : 'F'
			, 'brandG' : 'G'
			, 'brandH' : 'H'
			, 'brandI' : 'I'
			, 'brandJ' : 'J'
			, 'brandK' : 'K'
			, 'brandL' : 'L'
			, 'brandM' : 'M'
			, 'brandN' : 'N'
			, 'brandO' : 'O'
			, 'brandP' : 'P'
			, 'brandQ' : 'Q'
			, 'brandR' : 'R'
			, 'brandS' : 'S'
			, 'brandT' : 'T'
			, 'brandU' : 'U'
			, 'brandV' : 'V'
			, 'brandW' : 'W'
			, 'brandX' : 'X'
			, 'brandY' : 'Y'
			, 'brandZ' : 'Z'
			, 'brandEtc' : 'etc.'
		};
		
		for (i=0; i<brandIds.length; i++) {
			
			var brandList = dataArr[brandIds[i]];

			if (brandList.length > 0) {
				html += '<section id="' + brandIds[i] + '">';
				html += '<h3>' + dict_char[brandIds[i]] + '</h3>';
				html += '<ul>';
	
					for (j=0; j<brandList.length; j++) {
						if(brandList[j].brandId== 'GBRAND_0000000000130'){
							console.log(brandList[j]);
						}
						html += '<li>';
						html += '<figure>';
						/*if(brandList[j].brandExpsrSeCode == 'B2B'){
							if(brandList[j].brandBtbGoods!=null){
								html += '<img src='+brandList[j].brandBtbGoods.goodsImageList[0].goodsSmallImagePath+' style="width:100px;"></img>';
							}
						}else{
							if(brandList[j].brandBtcGoods!=null){
								html += '<img src='+brandList[j].brandBtcGoods.goodsImageList[0].goodsSmallImagePath+' style="width:100px;"></img>';
							}
						}*/
						/*if (brandList[j].brandImagePath != null && brandList[j].brandImagePath != '') {
							html += '<img src="' + brandList[j].brandImagePath + '" alt="' + brandList[j].brandNm + ' 로고" />';
						} else {
							html += '<figcaption>' + brandList[j].brandNm + '</figcaption>';
						}*/
						html += '<figcaption>' + brandList[j].brandNm + '</figcaption>';
						html += '</figure>';
						//html += '<cite>' + brandList[j].brandNm + '</cite>';
						
						if (brandList[j].brandIntGoods != null) {
							html += '<span>' + brandList[j].brandIntGoods + '</span>';
						}
						
						html += '<a  href="' + CTX_ROOT + '/shop/goods/brandGoodsList.do?searchGoodsBrandId=' + brandList[j].brandId + '">브랜드 바로가기</a>';
						html += '</li>';
					}
	
				html += '</ul>';
				html += '</section>';
			}
			
		}
		
		$('#brandList').html(html);

	}

})();
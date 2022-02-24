package modoo.cms.shop.goods.recomend.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.recomend.service.GoodsRecomendService;
import modoo.module.shop.goods.recomend.service.GoodsRecomendVO;

@Controller
public class CmsGoodsRecomendController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsRecomendController.class);
	
	@Resource(name = "goodsRecomendService")
	private GoodsRecomendService goodsRecomendService;
	
	/**
	 * 상품 추천 삭제
	 * @param goodsRecomend
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteRecomend.json")
	@ResponseBody JsonResult deleteRecomend(GoodsRecomendVO goodsRecomend) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(goodsRecomend.getGoodsRecomendNo() == null) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsRecomendNo 가 없음.");
			}else {
				
				goodsRecomendService.deleteGoodsRecomend(goodsRecomend);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
			}
			
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
		
	}
}

package modoo.cms.shop.goods.keyword.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.keyword.service.GoodsKeywordService;
import modoo.module.shop.goods.keyword.service.GoodsKeywordVO;

@Controller
public class CmsGoodsKeywordController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsKeywordController.class);
	
	@Resource(name = "goodsKeywordService")
	private GoodsKeywordService goodsKeywordService;
	
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsKeyword.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteGoodsKeyword(GoodsKeywordVO goodsKeyword) {
		JsonResult jsonResult = new JsonResult();
		try {
			if(goodsKeyword.getGoodsKeywordNo() == null) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsKeywordNo 가 없음.");
			}else {
				
				goodsKeywordService.deleteGoodsKeyword(goodsKeyword);
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

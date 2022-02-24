package modoo.cms.shop.goods.info.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.EgovMessageSource;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.info.service.GoodsItemService;
import modoo.module.shop.goods.info.service.GoodsItemVO;

@Controller
public class CmsGoodsItemController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsItemController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "goodsItemService")
	private GoodsItemService goodsItemService;
	
	/**
	 * 상품항목 삭제
	 * @param goodsItem
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsItem.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteGoodsItem(GoodsItemVO goodsItem) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(goodsItem.getGitemId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsItemId 가 없음.");
			}else {
				
				goodsItemService.deleteGoodsItem(goodsItem);
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

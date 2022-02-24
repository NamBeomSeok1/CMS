package modoo.cms.shop.goods.image.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.image.service.GoodsImageService;
import modoo.module.shop.goods.image.service.GoodsImageVO;

@Controller
public class CmsGoodsImageController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsImageController.class);
	
	@Resource(name = "goodsImageService")
	private GoodsImageService goodsImageService;
	
	/**
	 * 상품 이미지 삭제
	 * @param goodsImage
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsImage.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteGoodsImage(GoodsImageVO goodsImage) {
		JsonResult jsonResult = new JsonResult();
		try {
			if(goodsImage.getGoodsImageNo() == null) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsImageNo 가 없음.");
			}else {
				
				goodsImageService.deleteGoodsImage(goodsImage);
				
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

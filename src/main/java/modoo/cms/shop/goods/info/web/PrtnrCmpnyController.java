package modoo.cms.shop.goods.info.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;

@Controller
public class PrtnrCmpnyController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrtnrCmpnyController.class);
	
	@Resource(name = "prtnrCmpnyService")
	private PrtnrCmpnyService prtnrCmpnyService;
	
	/**
	 * 제휴사매핑 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/prtnrCmpnyList.json")
	@ResponseBody
	public JsonResult prtnrCmpnyList(@ModelAttribute("searchVO") PrtnrCmpnyVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			List<PrtnrCmpnyVO> resultList = prtnrCmpnyService.selectPrtnrCmpnyList(searchVO);
			jsonResult.put("list", resultList);
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}

}

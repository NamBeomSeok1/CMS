package modoo.cms.sale.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.banner.service.BannerService;
import modoo.module.banner.service.BannerVO;
import modoo.module.best.service.BestService;
import modoo.module.best.service.impl.BestVO;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.sale.service.SaleService;
import modoo.module.sale.service.impl.SaleVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller
public class CmsSaleController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsSaleController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "bannerService")
	private BannerService bannerService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "prtnrCmpnyService")
	private PrtnrCmpnyService prtnrCmpnyService;
	
	@Resource(name = "saleService")
	private SaleService saleService;

	@RequestMapping(value = "/decms/sale/saleManage.do")
	public String saleManage(Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//제휴사 목록
		List<?> prtnrList = prtnrCmpnyService.selectPrtnrCmpnyList(new PrtnrCmpnyVO());
		model.addAttribute("prtnrList", prtnrList);
		
	
		return "modoo/cms/sale/saleManage";
	}
	
	/**
	 * 베스트 목록 저장
	 * @param request
	 * @param bestList
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/sale/saveSaleGoods.json", method=RequestMethod.POST)
	public JsonResult saveSaleGoods(HttpServletRequest request, SaleVO searchVO, @RequestBody List<SaleVO> saleList, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			saleService.saveSaleGoods(searchVO, saleList);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/decms/sale/saleGoodsList.json", method=RequestMethod.GET)
	public JsonResult saleGoodsList(SaleVO searchVO, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			List<?> list = saleService.selectSaleGoodsList(searchVO);
			result.put("list", list);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}

}

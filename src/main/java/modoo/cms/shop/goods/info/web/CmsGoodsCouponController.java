package modoo.cms.shop.goods.info.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.info.exception.GoodsCouponException;
import modoo.module.shop.goods.info.service.GoodsCouponService;
import modoo.module.shop.goods.info.service.GoodsCouponVO;

@Controller
public class CmsGoodsCouponController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsCouponController.class);
	
	@Resource(name = "goodsCouponService")
	private GoodsCouponService goodsCouponService;
	
	/**
	 * 상품쿠폰 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/couponList.json")
	@ResponseBody
	public JsonResult couponList(GoodsCouponVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			
			if(StringUtils.isEmpty(searchVO.getGoodsId())) { // 임시 테이블 조회
				searchVO.setUploadGroupId(this.getUploadGroupId());
				List<GoodsCouponVO> resultList = goodsCouponService.selectTmpUploadCouponExcelList(searchVO);
				jsonResult.put("list", resultList);
			}else { // STN_GOODS_COUPON 조회
				List<GoodsCouponVO> resultList = goodsCouponService.selectGoodsCouponList(searchVO);
				List<GoodsCouponVO> list = new ArrayList<GoodsCouponVO>();
				for(GoodsCouponVO gcvo : resultList) {
					if("Y".equals(gcvo.getSleAt())) {
						gcvo.setDisabled(true);
					}
					list.add(gcvo);
				}
				jsonResult.put("list", list);
			}
			
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 상품쿠폰엑셀 업로드
	 * @param goodsCoupon
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/couponExcelUpload.json", method = RequestMethod.POST) 
	@ResponseBody
	public JsonResult couponExcelUpload(GoodsCouponVO goodsCoupon, final MultipartHttpServletRequest multiRequest) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		final MultipartFile excelFile = multiRequest.getFile("couponExcelFile");
		
		try {
			if(excelFile != null && !excelFile.isEmpty()) {
				
				//임시 데이터 삭제를 위해 (GoodsCouponExcelMapping.java 와 같아야한다.)
				goodsCoupon.setUploadGroupId(this.getUploadGroupId());
			
				//등록 카운트
				int resultCnt = goodsCouponService.uploadCouponExcel(goodsCoupon, excelFile.getInputStream());
				jsonResult.put("resultCnt", resultCnt);
				
				//임시 등록 목록
				//List<?> resultList = goodsCouponService.selectTmpUploadCouponExcelList(goodsCoupon);
				//jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}else {
				jsonResult.setMessage("엑셀 파일을 선택하세요");
				jsonResult.setSuccess(false);
			}
		}catch(GoodsCouponException ge) {
			loggerError(LOGGER, ge);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("이미 등록된 쿠폰이 있습니다.");
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품쿠폰번호 수정
	 * @param goodsCoupon
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/coupon/editCouponNo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editCouponNo(GoodsCouponVO goodsCoupon) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			
			if(StringUtils.isEmpty(goodsCoupon.getGoodsId())) { //임시 테이블 데이터 수정
				if(goodsCoupon.getGoodsCouponNo() == null) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage("고유번호가 없습니다.");

				}else if(StringUtils.isEmpty(goodsCoupon.getCouponNo())) {
					jsonResult.setSuccess(false);
					jsonResult.setEventCode("RECOVERY");
					jsonResult.setMessage("쿠폰번호를 입력하세요.");

				}else if(goodsCoupon.getCouponNo().length() > 60) {
					jsonResult.setSuccess(false);
					jsonResult.setEventCode("RECOVERY");
					jsonResult.setMessage("쿠폰번호 최대 길이는 60자입니다.");

				}else {
					goodsCoupon.setUploadGroupId(this.getUploadGroupId());
					if(goodsCouponService.selectTmpCouponCheckCnt(goodsCoupon) > 0) {
						jsonResult.setSuccess(false);
						jsonResult.setEventCode("RECOVERY");
						jsonResult.setMessage("이미 등록된 쿠폰입니다.");
					}else {
						goodsCouponService.updateTmpUploadCouponExcel(goodsCoupon);
					}
				}
			}else { // 등록 데이터 수정
				// TODO: 판매된 쿠폰은 수정 못하게 막어라
				
				goodsCouponService.updateGoodsCouponNo(goodsCoupon);
				
			}
			
			jsonResult.setSuccess(true);
		} catch(GoodsCouponException ge) {
			loggerError(LOGGER, ge);
			jsonResult.setSuccess(false);
			jsonResult.setEventCode("RECOVERY");
			jsonResult.setMessage(ge.getMessage());
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setEventCode("RECOVERY");
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품쿠폰 추가
	 * @param goodsCoupon
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/coupon/addCouponItem.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addCouponItem(GoodsCouponVO goodsCoupon) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(StringUtils.isEmpty(goodsCoupon.getCouponNo())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("쿠론번호가 없습니다.");
			}else {
				goodsCoupon.setRegisterId(user.getUniqId());
				goodsCoupon.setUploadGroupId(this.getUploadGroupId());
				
				if(StringUtils.isEmpty(goodsCoupon.getGoodsId())) {
					goodsCouponService.insertTmpCoupon(goodsCoupon);
				}else {
					goodsCoupon.setUploadGroupId(this.getUploadGroupId());
					goodsCoupon.setRegisterId(user.getUniqId());
					goodsCouponService.insertGoodsCoupon(goodsCoupon);
				}
				jsonResult.setSuccess(true);
			}

		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //등록이 실패하였습니다.
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 상품쿠폰 목록 삭제
	 * @param goodsCoupon
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/coupon/deleteCouponList.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCouponList(@RequestBody GoodsCouponVO goodsCoupon) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			
			if(StringUtils.isEmpty(goodsCoupon.getGoodsId())) { //임시 테이블 데이터 삭제 
				if(goodsCoupon.getSearchGoodsCouponNoList() == null || goodsCoupon.getSearchGoodsCouponNoList().size() == 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage("쿠폰을 선택하세요!");
				}else {
					
					goodsCouponService.deleteTmpCouponList(goodsCoupon);
				}

			}else {
				goodsCouponService.deleteGoodsCoupon(goodsCoupon);
				
			}
			
			jsonResult.setSuccess(true);
			jsonResult.setMessage("삭제되었습니다.");

		} catch(GoodsCouponException ge) {
			loggerError(LOGGER, ge);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(ge.getMessage());
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}

	/**
	 * UPLOAD_GROUP_ID
	 * @return
	 */
	private String getUploadGroupId() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String uploadGroupId = user.getUniqId(); //(StringUtils.isEmpty(user.getCmpnyId())?"SYSTEM":user.getCmpnyId()) + user.getUniqId();
		return uploadGroupId;
	}
}

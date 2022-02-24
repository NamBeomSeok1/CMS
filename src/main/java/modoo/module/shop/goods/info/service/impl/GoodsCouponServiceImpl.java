package modoo.module.shop.goods.info.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.APIUtil;
import modoo.module.common.util.NumberUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import modoo.module.shop.goods.info.exception.GoodsCouponException;
import modoo.module.shop.goods.info.service.GoodsCouponService;
import modoo.module.shop.goods.info.service.GoodsCouponVO;

@Service("goodsCouponService")
public class GoodsCouponServiceImpl extends EgovAbstractServiceImpl implements GoodsCouponService {
	
	@Resource(name = "goodsCouponExcelService")
	private EgovExcelService goodsCouponExcelService;
	
	@Resource(name = "goodsCouponMapper")
	private GoodsCouponMapper goodsCouponMapper;

	/**
	 * 임시 상품쿠폰 저장
	 */
	@Override
	public void insertTmpCoupon(GoodsCouponVO goodsCoupon) throws Exception {
		goodsCouponMapper.insertTmpCoupon(goodsCoupon);
	}
	
	/**
	 * 업로드쿠폰엑셀
	 */
	@Override
	public int uploadCouponExcel(GoodsCouponVO goodsCoupon, InputStream fis) throws Exception {
		
		// 임시 데이터 삭제 (truncate 시키지 말 것 )
		goodsCouponMapper.deleteTmpUploadCouponExcel(goodsCoupon);
		int resultCnt = goodsCouponExcelService.uploadExcel("insertUploadGoodsCoupon", fis, 1, 0, new XSSFWorkbook());
		if(StringUtils.isNotEmpty(goodsCoupon.getGoodsId())) { // goodsId 가있다 (수정모드 일때)
			
			//기존데이터와 엑셀데이터간의 중복체크
			List<?> dupList = goodsCouponMapper.selectGoodsCouponDuplList(goodsCoupon);
			if(dupList.size() > 0) {
				throw new GoodsCouponException("이미 등록된 쿠폰번호가 있습니다.");
			}else {
				goodsCouponMapper.insertTmpCouponToGoodsCoupon(goodsCoupon);
				goodsCouponMapper.deleteTmpUploadCouponExcel(goodsCoupon);
			}
		}
		return resultCnt;
	}

	/**
	 * 임비 쿠폰 목록
	 */
	@Override
	public List<GoodsCouponVO> selectTmpUploadCouponExcelList(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectTmpUploadCouponExcelList(goodsCoupon);
	}
	
	/**
	 * 임시 쿠폰데이터 수정
	 */
	@Override
	public void updateTmpUploadCouponExcel(GoodsCouponVO goodsCoupon) throws Exception {
		goodsCouponMapper.updateTmpUploadCouponExcel(goodsCoupon);
	}

	/**
	 * 쿠폰 체크 카운트
	 */
	@Override
	public int selectTmpCouponCheckCnt(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectTmpCouponCheckCnt(goodsCoupon);
	}

	/**
	 * 임시쿠폰 목록 삭제
	 */
	@Override
	public void deleteTmpCouponList(GoodsCouponVO goodsCoupon) throws Exception {
		goodsCouponMapper.deleteTmpCouponList(goodsCoupon);
	}

	/**
	 * 쿠폰목록
	 */
	@Override
	public List<GoodsCouponVO> selectGoodsCouponList(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectGoodsCouponList(goodsCoupon);
	}
	
	@Override
	public int selectGoodsCouponCnt(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectGoodsCouponCnt(goodsCoupon);
	}

	/**
	 * 쿠폰번호 수정
	 */
	@Override
	public void updateGoodsCouponNo(GoodsCouponVO goodsCoupon) throws Exception {
		GoodsCouponVO gc = goodsCouponMapper.selectGoodsCouponDetail(goodsCoupon);
		if(StringUtils.isNotEmpty(gc.getOrderNo())) {
			throw new GoodsCouponException("이미 판매된 쿠폰입니다.");
		}
		goodsCouponMapper.updateGoodsCouponNo(goodsCoupon);
	}
	
	/**
	 * 쿠폰주문번호수정
	 */
	@Override
	public void updateGoodsCouponOrderNo(GoodsCouponVO goodsCoupon) throws Exception {
		goodsCouponMapper.updateGoodsCouponOrderNo(goodsCoupon);
	}

	/**
	 * 쿠폰 삭제
	 */
	@Override
	public void deleteGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception {
		List<?> sleCheckList = goodsCouponMapper.selectGoodsCouponSleCheckList(goodsCoupon);
		if(sleCheckList.size() > 0) {
			throw new GoodsCouponException("이미 판매된 쿠폰이 포함되어 있습니다.");
		}
		goodsCouponMapper.deleteGoodsCoupon(goodsCoupon);
	}

	/**
	 * 쿠폰저장
	 */
	@Override
	public void insertGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception {
		//String couponNo  = NumberUtil.numberGen(4)+System.currentTimeMillis();
		String couponNo  = NumberUtil.couponGen();

		goodsCoupon.setCouponNo(couponNo);

		goodsCouponMapper.insertGoodsCoupon(goodsCoupon);
		goodsCoupon = goodsCouponMapper.selectGoodsCoupon(goodsCoupon);
		sendCouponInfoApi(goodsCoupon);

		//시퀀스 넘버는 STT_UPLOAD_COUPON 에서 갖고오기 때문에 먼저 생성하고 복사해온다.
		
		/*goodsCouponMapper.deleteTmpUploadCouponExcel(goodsCoupon); // 임시 저장 테이블 비우기

		goodsCouponMapper.insertTmpCoupon(goodsCoupon);
		goodsCouponMapper.insertTmpCouponToGoodsCoupon(goodsCoupon);

		goodsCouponMapper.deleteTmpUploadCouponExcel(goodsCoupon); // 임시 저장 테이블 비우기*/
		
	}

	/**
	 * 쿠폰 정보 폭스클래스 전송 api
	 */
	public void sendCouponInfoApi(GoodsCouponVO goodsCoupon) throws Exception{

		HashMap<String,String> resultMap = new HashMap<>();

		URL url = new URL("http://dev-class.foxedu.kr/api/coupon/insertCoupon.json");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("mberId",goodsCoupon.getOrdrrId());
		jsonObject.put("no",goodsCoupon.getCouponNo());
		jsonObject.put("kndCode",goodsCoupon.getCouponKndCode());
		jsonObject.put("nm",goodsCoupon.getCouponNm());
		jsonObject.put("bgnde",goodsCoupon.getCouponBeginPnttm());
		jsonObject.put("endde",goodsCoupon.getCouponEndPnttm());
		jsonObject.put("sttusCode",goodsCoupon.getCouponSttusCode());

		String jsonString = APIUtil.postUrlBodyJson(url,jsonObject.toString());
		JSONObject jObj = (JSONObject) JSONSerializer.toJSON(jsonString);
		String success = jObj.getString("success");
		if("true".equals(success)){
			resultMap.put("successYn","Y");
		}else{
			String message = jObj.getString("message");
			System.out.println(message+"쿠폰 발송 실패 메세지!!!!!!!!");
			resultMap.put("successYn","N");
			resultMap.put("message",message);
		}
	}

	/**
	 * 쿠폰 상태 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	@Override
	public void updateGoodsCouponSttus(GoodsCouponVO goodsCoupon) throws Exception {
		goodsCouponMapper.updateGoodsCouponSttus(goodsCoupon);
	}

	/**
	 * 쿠폰상세
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	@Override
	public GoodsCouponVO selectGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectGoodsCoupon(goodsCoupon);
	}

	/**
	 * 쿠폰발급
	 */
	@Override
	public List<GoodsCouponVO> selectSleGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception {
		return goodsCouponMapper.selectSleGoodsCoupon(goodsCoupon);
	}

}

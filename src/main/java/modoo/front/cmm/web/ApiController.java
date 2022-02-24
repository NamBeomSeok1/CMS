package modoo.front.cmm.web;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.info.service.GoodsCouponService;
import modoo.module.shop.goods.info.service.GoodsCouponVO;
import modoo.module.shop.goods.info.service.GoodsService;
import net.sf.json.JSONObject;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ApiController{

	@Resource(name = "goodsCouponService")
	private GoodsCouponService goodsCouponService;

//도로명 API폼 
	@RequestMapping(value="/api/zipSearch.do", method=RequestMethod.GET)
	public String zipSearchMove(){
	
		return "modoo/front/shop/order/address";
	}

	/**
	 * 수강권 쿠폰 유효성 체크
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/api/couponValid.json",method = {RequestMethod.GET,RequestMethod.POST})
	public void validVoucherCoupon(@RequestBody HashMap<String,String> data,HttpServletResponse response) throws Exception {
		String successYn = "Y";
		String message = "";

		String couponNo = String.valueOf(data.get("couponNo"));
		GoodsCouponVO goodsCouponVO = new GoodsCouponVO();
		goodsCouponVO.setCouponNo(couponNo);

		List<GoodsCouponVO> list = goodsCouponService.selectGoodsCouponList(goodsCouponVO);
		if(list.size()<1){
			message="유효하지 않은 쿠폰번호입니다.";
			successYn="N";
		}else{
			goodsCouponVO = goodsCouponService.selectGoodsCoupon(goodsCouponVO);
			successYn = (StringUtils.equals(goodsCouponVO.getCouponSttusCode(),"USE")||StringUtils.equals(goodsCouponVO.getCouponSttusCode(),"CANCL"))?"N":"Y";
		}

		JSONObject jObj = new JSONObject();
		response.setContentType("application/json;charset=utf-8");

		jObj.put("successYn",successYn);
		jObj.put("message",message);

		PrintWriter printWriter = response.getWriter();
		printWriter.println(jObj.toString());
		printWriter.flush();
		printWriter.close();

	}
	
	

}

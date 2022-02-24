package modoo.module.biling.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.text.SimpleDateFormat;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.util.CommonUtils;
import modoo.module.shop.goods.setle.service.OrderSetleVO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;



public class Biling {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Biling.class);
	
	//암호화
	Encryption encryption = new Encryption(); 
	/*
	 * 빌링 KG이니시스 통신
	 */
	public HashMap<String, Object> biling(HashMap<String, Object> map){
		HashMap<String, Object> resultMap = new HashMap<>();
		java.math.BigDecimal orderNo = (BigDecimal)map.get("orderGroupNo");
		BilingVO vo = new BilingVO();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(date);
		
		vo.setTimeStamp(now);
		
		try {
			//카드정보
			vo.setCardNumber((String)map.get("cardNo"));
			vo.setCardExpire((String)map.get("cardUsgpd"));
			vo.setRegNo((String)map.get("brthdy"));
			vo.setCardPw((String)map.get("cardPassword"));

			//유저정보
			vo.setBuyerEmail((String)map.get("userEmail"));
			vo.setBuyerName((String)map.get("userName"));
			vo.setBuyerTel((String)map.get("userTelno"));
			
			//상품정보
			vo.setGoodName((String)map.get("goodsNm"));
			vo.setMoid(String.valueOf((BigDecimal)map.get("orderGroupNo")));
			
			/*빌링키 발급 HASH키 생성*/
			vo.setType("Auth");
			
			java.math.BigDecimal goodsPc= (BigDecimal)map.get("goodsPc");
			vo.setPrice(String.valueOf(goodsPc));
			
			String inputval = encryption.getKey()+vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+orderNo+vo.getPrice()+vo.getCardNumber();
			byte[] bytes = inputval.getBytes(StandardCharsets.UTF_8);
			
			String utf8EncinputVal = new String(bytes, StandardCharsets.UTF_8);
			System.out.println("해쉬 전 값"+utf8EncinputVal);
			String keyHashData = encryption.hashKey(utf8EncinputVal);
			System.out.println("해쉬 후 값 :"+keyHashData);
			
			vo.setHashData(keyHashData);
			/*HASH키 생성끝*/
			
			/*이니시스 API 통신*/
			HttpPost httpPost = new HttpPost("https://iniapi.inicis.com/api/v1/billing");
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			postParams.add(new BasicNameValuePair("type", vo.getType()));
			postParams.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
			postParams.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
			postParams.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
			postParams.add(new BasicNameValuePair("mid", vo.getMid()));
			postParams.add(new BasicNameValuePair("url", vo.getUrl()));
			postParams.add(new BasicNameValuePair("moid", vo.getMoid()));
			postParams.add(new BasicNameValuePair("goodName",vo.getGoodName()));
			postParams.add(new BasicNameValuePair("buyerName", vo.getBuyerName()));
			postParams.add(new BasicNameValuePair("buyerEmail", vo.getBuyerEmail()));
			postParams.add(new BasicNameValuePair("buyerTel", vo.getBuyerTel()));
			postParams.add(new BasicNameValuePair("price", vo.getPrice()));
			postParams.add(new BasicNameValuePair("cardNumber", vo.getCardNumber()));
			postParams.add(new BasicNameValuePair("cardExpire", vo.getCardExpire()));
			postParams.add(new BasicNameValuePair("regNo", vo.getRegNo()));
			postParams.add(new BasicNameValuePair("cardPw", vo.getCardPw()));
			postParams.add(new BasicNameValuePair("hashData", vo.getHashData()));
			
			 org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			 
			 httpPost.setEntity(postEntity);
			 
			 CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			 CloseableHttpResponse response = httpClient.execute(httpPost);
			 
		    ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        LOGGER.info("[빌링키 발급 내용]"+body.toString());
	        Map<String, String> bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());   
	        String resultMsg = bodyMap.get("resultMsg");
	        String resultCode = bodyMap.get("resultCode");
		    if(resultCode.equals("00") && response.getStatusLine().getStatusCode()==200){
		    	//빌링승인
		    	//빌링키 넣기
		    	String billkey = bodyMap.get("billKey");
		    	resultMap.put("billKey", billkey);

		    	vo.setType("Billing");
				String inputval2 = encryption.getKey()+vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+orderNo+vo.getPrice()+billkey;
				byte[] bytes2 = inputval2.getBytes(StandardCharsets.UTF_8);
				
				String utf8EncinputVal2 = new String(bytes2, StandardCharsets.UTF_8);
				String keyHashData2 = encryption.hashKey(utf8EncinputVal2);
				
				vo.setHashData(keyHashData2);
				HttpPost httpPost2 = new HttpPost("https://iniapi.inicis.com/api/v1/billing");
				
				List<NameValuePair> postParams2 = new ArrayList<NameValuePair>();
		    	/*이니시스  Biling API 통신*/
				postParams2.add(new BasicNameValuePair("type", vo.getType()));
				postParams2.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
				postParams2.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
				postParams2.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
				postParams2.add(new BasicNameValuePair("mid", vo.getMid()));
				postParams2.add(new BasicNameValuePair("url", vo.getUrl()));
				postParams2.add(new BasicNameValuePair("moid", vo.getMoid()));
				postParams2.add(new BasicNameValuePair("goodName",vo.getGoodName()));
				postParams2.add(new BasicNameValuePair("buyerName", vo.getBuyerName()));
				postParams2.add(new BasicNameValuePair("buyerEmail", vo.getBuyerEmail()));
				postParams2.add(new BasicNameValuePair("buyerTel", vo.getBuyerTel()));
				postParams2.add(new BasicNameValuePair("price", vo.getPrice()));
				postParams2.add(new BasicNameValuePair("cardNumber", vo.getCardNumber()));
				postParams2.add(new BasicNameValuePair("cardExpire", vo.getCardExpire()));
				postParams2.add(new BasicNameValuePair("regNo", vo.getRegNo()));
				postParams2.add(new BasicNameValuePair("cardPw", vo.getCardPw()));
				postParams2.add(new BasicNameValuePair("hashData", vo.getHashData()));
				postParams2.add(new BasicNameValuePair("billKey", billkey));
				postParams2.add(new BasicNameValuePair("authentification", "00"));
				 
				postEntity = new UrlEncodedFormEntity(postParams2, "UTF-8");
				 
				httpPost2.setEntity(postEntity);
				 
				httpClient = HttpClientBuilder.create().build();
				response = httpClient.execute(httpPost2);
				 
			    handler = new BasicResponseHandler();
		        body = handler.handleResponse(response);
		        LOGGER.info("[빌링 승인 내용]"+body.toString());
		        bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());   
		        resultCode = bodyMap.get("resultCode");
		        resultMsg = bodyMap.get("resultMsg");
		        if(response.getStatusLine().getStatusCode()==200 && resultCode.equals("00")){
		        	String tid = bodyMap.get("tid");
		        	resultMap.put("iniCode", tid);
		        	resultMap.put("success", true);
		        	resultMap.put("resultMsg", resultMsg);
		        	resultMap.put("resultCode", "R000");
		        	resultMap.put("totPc",vo.getPrice());
		        }else{
		        	resultMap.put("success", false);
		        	resultMap.put("resultMsg", resultMsg);
		        	resultMap.put("resultCode", "R001");
		        	LOGGER.error("빌링승인과정에서 에러 발생 에러내용"+resultMsg);
		        }
		    }else{
		    	LOGGER.error("빌링키발급과정에서 에러 발생 에러내용"+resultMsg);
		    	resultMap.put("resultMsg", resultMsg);
		    	resultMap.put("success", false);
		    	resultMap.put("resultCode", "R003");
		    }
		    /*API 통신 끝*/
		} catch (Exception e) {
			LOGGER.error("HTTP통신에러"+e);
			resultMap.put("resultMsg", "이니시스 빌링 승인 API - HTTP 통신 오류");
			resultMap.put("resultCode", "R002");
			resultMap.put("success", false);
		}
		return resultMap;
	
	}
	/*
	 * 빌링키 발급
	 */
	public HashMap<String, Object> bilingKey(HashMap<String, Object> map){
		
		HashMap<String, Object> resultMap = new HashMap<>();
		java.math.BigDecimal orderNo = (BigDecimal)map.get("orderGroupNo");
		BilingVO vo = new BilingVO();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(date);
		
		vo.setTimeStamp(now);
		
		try {
			//카드정보
			vo.setCardNumber((String)map.get("cardNo"));
			vo.setCardExpire((String)map.get("cardUsgpd"));
			vo.setRegNo((String)map.get("brthdy"));
			vo.setCardPw((String)map.get("cardPassword"));

			//유저정보
			vo.setBuyerEmail((String)map.get("userEmail"));
			vo.setBuyerName((String)map.get("userName"));
			vo.setBuyerTel((String)map.get("userTelno"));
			
			//상품정보
			vo.setGoodName((String)map.get("goodsNm"));
			vo.setMoid(String.valueOf((BigDecimal)map.get("orderGroupNo")));
			
			/*빌링키 발급 HASH키 생성*/
			vo.setType("Auth");
			
			vo.setPrice("0");
			
			String inputval = encryption.getKey()+vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+orderNo+vo.getPrice()+vo.getCardNumber();
			byte[] bytes = inputval.getBytes(StandardCharsets.UTF_8);
			
			String utf8EncinputVal = new String(bytes, StandardCharsets.UTF_8);
			System.out.println("해쉬 전 값"+utf8EncinputVal);
			String keyHashData = encryption.hashKey(utf8EncinputVal);
			System.out.println("해쉬 후 값 :"+keyHashData);
			
			vo.setHashData(keyHashData);
			/*HASH키 생성끝*/
			
			/*이니시스 API 통신*/
			HttpPost httpPost = new HttpPost("https://iniapi.inicis.com/api/v1/billing");
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			postParams.add(new BasicNameValuePair("type", vo.getType()));
			postParams.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
			postParams.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
			postParams.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
			postParams.add(new BasicNameValuePair("mid", vo.getMid()));
			postParams.add(new BasicNameValuePair("url", vo.getUrl()));
			postParams.add(new BasicNameValuePair("moid", vo.getMoid()));
			postParams.add(new BasicNameValuePair("goodName",vo.getGoodName()));
			postParams.add(new BasicNameValuePair("buyerName", vo.getBuyerName()));
			postParams.add(new BasicNameValuePair("buyerEmail", vo.getBuyerEmail()));
			postParams.add(new BasicNameValuePair("buyerTel", vo.getBuyerTel()));
			postParams.add(new BasicNameValuePair("price", vo.getPrice()));
			postParams.add(new BasicNameValuePair("cardNumber", vo.getCardNumber()));
			postParams.add(new BasicNameValuePair("cardExpire", vo.getCardExpire()));
			postParams.add(new BasicNameValuePair("regNo", vo.getRegNo()));
			postParams.add(new BasicNameValuePair("cardPw", vo.getCardPw()));
			postParams.add(new BasicNameValuePair("hashData", vo.getHashData()));
			
			 org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			 
			 httpPost.setEntity(postEntity);
			 
			 CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			 CloseableHttpResponse response = httpClient.execute(httpPost);
			 
		    ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        LOGGER.info("[빌링키 발급 내용]"+body.toString());
	        Map<String, String> bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());   
	        String resultCode = bodyMap.get("resultCode");
	        String resultMsg = bodyMap.get("resultMsg");
		    if(resultCode.equals("00") && response.getStatusLine().getStatusCode()==200){
		    	resultMap.put("billKey", bodyMap.get("billKey"));
		    	resultMap.put("success", true);
		    	resultMap.put("resultMsg", resultMsg);
	        	resultMap.put("resultCode", "R000");
		    }else{
		    	LOGGER.error("빌키발급중 오류"+resultMsg);
		    	resultMap.put("resultMsg", resultMsg);
		    	resultMap.put("success", false);
		    	resultMap.put("resultCode", "R003");
		    }
	    }catch (Exception e) {
	    	LOGGER.error("빌키발급중 HTTP ERROR"+e);
	    	resultMap.put("resultMsg", "이니시스 빌링 승인 API - 기타 오류");
	    	resultMap.put("success", false);
	    	resultMap.put("resultCode", "R003");
	    }
		return resultMap;
	}
	
	/*
	 * KG이니시스 단일 주문건 취소
	 * 
	 */
	public void cancelCardSetle(EgovMap setleVO, OrderSetleVO setleInfo) throws Exception{
		BilingVO vo = new BilingVO();
		Encryption encryption = new Encryption();

		vo.setType("Refund");
		vo.setTid((String) setleVO.get("iniSetleConfmNo"));

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		vo.setTimeStamp(format.format(date));

		/******일반상품일땐 모듈 key,iv,mid변경******/
		if("GNR".equals(setleVO.get("orderKndCode"))&& StringUtils.isNotEmpty((String)setleVO.get("goPayMethod"))){
			vo.setMid(EgovProperties.getProperty("INICIS.mid"));
			encryption.setKeyIv(EgovProperties.getProperty("INICIS.key"), EgovProperties.getProperty("INICIS.iv"));
			vo.setPayMethod((String)setleVO.get("goPayMethod"));
		}
		
		String inputval = encryption.getKey()+ vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+vo.getTid();
		byte[] bytes = inputval.getBytes(StandardCharsets.UTF_8);
		String utf8EncinputVal = new String(bytes, StandardCharsets.UTF_8);
		String keyHashData = encryption.hashKey(utf8EncinputVal);
		vo.setHashData(keyHashData);
		
		try {
	
			/*이니시스 API 통신*/
			HttpPost httpPost = new HttpPost("https://iniapi.inicis.com/api/v1/refund"); //결제 취소 URL
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			//취소 요청 파라미터
			postParams.add(new BasicNameValuePair("type", vo.getType()));
			postParams.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
			postParams.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
			postParams.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
			postParams.add(new BasicNameValuePair("mid", vo.getMid()));
			postParams.add(new BasicNameValuePair("tid", vo.getTid()));
			postParams.add(new BasicNameValuePair("msg", "취소 요청 메시지"));
			postParams.add(new BasicNameValuePair("hashData", vo.getHashData()));
			
			org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			httpPost.setEntity(postEntity);
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			Map<String, String> bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());
			String resultMsg = bodyMap.get("resultMsg");
	        String resultCode = bodyMap.get("resultCode");
		
	        if( resultCode.equals("00") && response.getStatusLine().getStatusCode() == 200 ){
	        	
	        	System.out.println("=============================================요청 성공!");
	        	System.out.println(body);
	        	System.out.println(resultMsg.toString());
	        	System.out.println(body.toString());
	        	
	        	setleInfo.setSetleSttusCode("C");
	        	setleInfo.setSetleResultCode("R000");
	        } else {
	        	
	        	System.out.println("=============================================요청 실패!");
	        	System.out.println(body);
	        	System.out.println(resultMsg.toString());
	        	System.out.println(body.toString());
	        	
	        	setleInfo.setSetleSttusCode("F");
	        	setleInfo.setSetleResultCode(resultCode);
	        }

	        setleInfo.setSetleResultMssage(resultMsg.toString());

		} catch (Exception e) {
			System.out.println("=============================================이니시스 빌링 승인 API - HTTP 통신 오류!");
			setleInfo.setSetleResultCode("R002");
			
		}
	
		setleInfo.setUseAt("Y");
		setleInfo.setEzwSetleConfmNo(null);
		//setleInfo.setIniSetleConfmNo(null);
		//setleInfo.setSetlePrarnde(null);
		setleInfo.setTempKey(null);

	}
	
	/*
	 * KG이니시스 부분취소
	 */
	public void partialCancelCardSetle(EgovMap setleVO, OrderSetleVO setleInfo) throws Exception{
		
		BilingVO vo = new BilingVO();
		Encryption encryption = new Encryption();

		vo.setType("PartialRefund");
		vo.setTid((String) setleVO.get("iniSetleConfmNo"));
		vo.setTimeStamp(CommonUtils.getCurrentDateTime());
		vo.setPrice(String.valueOf(setleVO.get("setleCardAmount"))); //취소 요청 금액
		vo.setConfirmPrice(String.valueOf(setleVO.get("confirmPrice"))); //부분취소 후 남은 금액
		vo.setCurrency("WON");

		//첫글자 대문자, 나머지 소문자로 표시 - (Card)

		setleInfo.setSetleCardAmount(new BigDecimal(String.valueOf(setleVO.get("setleCardAmount"))));
		setleInfo.setSetleTotAmount(new BigDecimal(String.valueOf(setleVO.get("setleCardAmount"))));
		
		String inputval = encryption.getKey()+ vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+vo.getTid()+vo.getPrice()+vo.getConfirmPrice();

		/******일반상품일땐 모듈 key,iv,mid변경******/
		if("GNR".equals(String.valueOf(setleVO.get("orderKndCode")))&&StringUtils.isNotEmpty((String)setleVO.get("goPayMethod"))){
			String payMethod = (String)setleVO.get("goPayMethod");
			payMethod = payMethod.toLowerCase();
			char[] methodArr = payMethod.toCharArray();
			methodArr[0] = Character.toUpperCase(methodArr[0]);
			vo.setPayMethod(new String(methodArr));
			vo.setMid(EgovProperties.getProperty("INICIS.mid"));
			encryption.setKeyIv(EgovProperties.getProperty("INICIS.key"), EgovProperties.getProperty("INICIS.iv"));

			//첫글자 대문자, 나머지 소문자로 표시 - (Card)
			payMethod = (String)setleVO.get("goPayMethod");
			payMethod = payMethod.toLowerCase();
			methodArr = payMethod.toCharArray();
			methodArr[0] = Character.toUpperCase(methodArr[0]);
			vo.setPayMethod(new String(methodArr));

			inputval = encryption.getKey()+vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+vo.getTid()+vo.getPrice()+vo.getConfirmPrice();

			if("HPP".equals(setleVO.get("setleResultTyCode"))){
				//휴대폰결제는 전체취소
				vo.setType("Refund");
				vo.setPayMethod((String)setleVO.get("setleResultTyCode"));
				inputval = encryption.getKey()+""+vo.getType()+""+vo.getPayMethod()+""+vo.getTimeStamp()+""+vo.getClientIp()+""+vo.getMid()+""+vo.getTid();
			}
		}

		byte[] bytes = inputval.getBytes(StandardCharsets.UTF_8);
		String utf8EncinputVal = new String(bytes, StandardCharsets.UTF_8);
		String keyHashData = encryption.hashKey(utf8EncinputVal);
		vo.setHashData(keyHashData);
		
		try {
	
			/*이니시스 API 통신*/
			HttpPost httpPost = new HttpPost("https://iniapi.inicis.com/api/v1/refund"); //결제 취소 URL
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			//필수 요청 파라미터
			postParams.add(new BasicNameValuePair("type", vo.getType()));
			postParams.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
			postParams.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
			postParams.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
			postParams.add(new BasicNameValuePair("mid", vo.getMid()));
			postParams.add(new BasicNameValuePair("tid", vo.getTid()));
			postParams.add(new BasicNameValuePair("msg", "부분취소 요청 메시지"));
			postParams.add(new BasicNameValuePair("price", vo.getPrice()));
			postParams.add(new BasicNameValuePair("confirmPrice", vo.getConfirmPrice()));
			postParams.add(new BasicNameValuePair("currency", vo.getCurrency())); // 통화코드, 필수 아님
			postParams.add(new BasicNameValuePair("hashData", vo.getHashData()));
			
			org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			httpPost.setEntity(postEntity);
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			Map<String, String> bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());
			String resultMsg = bodyMap.get("resultMsg");
	        String resultCode = bodyMap.get("resultCode");
		
	        if( resultCode.equals("00") && response.getStatusLine().getStatusCode() == 200 ){
	        	System.out.println("=============================================요청 성공!");
	        	System.out.println(body);
	        	System.out.println(resultMsg.toString());
	        	System.out.println(body.toString());
	        	setleInfo.setSetleSttusCode("C");
	        	setleInfo.setSetleResultCode("R000");
	        } else {
	        	System.out.println("=============================================요청 실패!");
	        	System.out.println(body);
	        	System.out.println(resultMsg.toString());
	        	System.out.println(body.toString());
	        	setleInfo.setSetleSttusCode("F");
	        	setleInfo.setSetleResultCode(resultCode);
	        }
	        setleInfo.setSetleResultMssage(resultMsg.toString());

		} catch (Exception e) {
			System.out.println("=============================================이니시스 빌링 승인 API - HTTP 통신 오류!");
			e.printStackTrace();
			setleInfo.setSetleResultCode("R002");
		}

	}
	/*
	 * 카드 검증 빌링키 발급
	 */
	public HashMap<String, Object> CardbilingKey(HashMap<String, String> encMap){
		Encryption encryption = new Encryption();
		HashMap<String, Object> resultMap = new HashMap<>();
		BilingVO vo = new BilingVO();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(date);
		
		vo.setTimeStamp(now);
		
		try {
			vo.setCardNumber((String)encMap.get("cardNo"));
			vo.setCardExpire((String)encMap.get("cardUsgpd"));
			vo.setRegNo((String)encMap.get("brthdy"));
			vo.setCardPw((String)encMap.get("cardPassword"));

			//유저정보
			vo.setBuyerEmail("support@foxedu.co.kr");
			vo.setBuyerName("사용자");
			vo.setBuyerTel("00000000000");
			
			//상품정보
			vo.setGoodName("상품");
			vo.setMoid("00");
			
			/*빌링키 발급 HASH키 생성*/
			vo.setType("Auth");
			
			vo.setPrice("0");
			
			String inputval = encryption.getKey()+vo.getType()+vo.getPayMethod()+vo.getTimeStamp()+vo.getClientIp()+vo.getMid()+vo.getMoid()+vo.getPrice()+vo.getCardNumber();
			byte[] bytes = inputval.getBytes(StandardCharsets.UTF_8);
			
			String utf8EncinputVal = new String(bytes, StandardCharsets.UTF_8);
			System.out.println("해쉬 전 값"+utf8EncinputVal);
			String keyHashData = encryption.hashKey(utf8EncinputVal);
			System.out.println("해쉬 후 값 :"+keyHashData);
			
			vo.setHashData(keyHashData);
			/*HASH키 생성끝*/
			
			/*이니시스 API 통신*/
			HttpPost httpPost = new HttpPost("https://iniapi.inicis.com/api/v1/billing");
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			postParams.add(new BasicNameValuePair("type", vo.getType()));
			postParams.add(new BasicNameValuePair("paymethod", vo.getPayMethod()));
			postParams.add(new BasicNameValuePair("timestamp", vo.getTimeStamp()));
			postParams.add(new BasicNameValuePair("clientIp", vo.getClientIp()));
			postParams.add(new BasicNameValuePair("mid", vo.getMid()));
			postParams.add(new BasicNameValuePair("url", vo.getUrl()));
			postParams.add(new BasicNameValuePair("moid", vo.getMoid()));
			postParams.add(new BasicNameValuePair("goodName",vo.getGoodName()));
			postParams.add(new BasicNameValuePair("buyerName", vo.getBuyerName()));
			postParams.add(new BasicNameValuePair("buyerEmail", vo.getBuyerEmail()));
			postParams.add(new BasicNameValuePair("buyerTel", vo.getBuyerTel()));
			postParams.add(new BasicNameValuePair("price", vo.getPrice()));
			postParams.add(new BasicNameValuePair("cardNumber", vo.getCardNumber()));
			postParams.add(new BasicNameValuePair("cardExpire", vo.getCardExpire()));
			postParams.add(new BasicNameValuePair("regNo", vo.getRegNo()));
			postParams.add(new BasicNameValuePair("cardPw", vo.getCardPw()));
			postParams.add(new BasicNameValuePair("hashData", vo.getHashData()));
			
			 org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			 
			 httpPost.setEntity(postEntity);
			 
			 CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			 CloseableHttpResponse response = httpClient.execute(httpPost);
			 
		    ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        LOGGER.info("[빌링키 발급 내용]"+body.toString());
	        Map<String, String> bodyMap = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>(){}.getType());   
	        String resultCode = bodyMap.get("resultCode");
	        String resultMsg = bodyMap.get("resultMsg");
	        String cardCode = bodyMap.get("cardCode");
		    if(resultCode.equals("00") && response.getStatusLine().getStatusCode()==200){
		    	resultMap.put("billKey", bodyMap.get("billKey"));
		    	resultMap.put("success", true);
		    	resultMap.put("resultMsg", resultMsg);
		    	resultMap.put("cardCode", cardCode);
		    }else{
		    	LOGGER.error("빌키발급중 오류"+resultMsg);
		    	resultMap.put("resultMsg", resultMsg);
		    	resultMap.put("success", false);
		    	resultMap.put("cardCode", cardCode);
		    }
	    }catch (Exception e) {
	    	LOGGER.error("빌키발급중 HTTP ERROR"+e);
	    	resultMap.put("success", false);
	    }
		return resultMap;
	}
}

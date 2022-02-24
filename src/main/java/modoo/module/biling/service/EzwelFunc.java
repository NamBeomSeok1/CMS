package modoo.module.biling.service;

import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.mber.info.service.MberVO;
import modoo.module.mber.sso.service.EzwelCryptoModule;
import modoo.module.shop.goods.setle.service.OrderSetleVO;

public class EzwelFunc {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EzwelFunc.class); 
	
	
		//ezwel 포인트조회
		public java.math.BigDecimal ezwelPointSearch(String userKey,String clientCd){
			
			//ezwel 암호화 모듈
			EzwelCryptoModule ezwelEncrytion = new EzwelCryptoModule();
			
			java.math.BigDecimal point=null;
			
			try {
				
			HttpPost httpPost = new HttpPost("http://newasp.ezwel.com/aspReceiver.ez");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();

			postParams.add(new BasicNameValuePair("cspCd",EgovProperties.getProperty("SSO.ezwel.cspCd")));
			postParams.add(new BasicNameValuePair("userKey", ezwelEncrytion.encode(userKey)));
			postParams.add(new BasicNameValuePair("command", ezwelEncrytion.encode("110")));
			postParams.add(new BasicNameValuePair("clientCd",ezwelEncrytion.encode(clientCd)));
			org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			httpPost.setEntity(postEntity);
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
				 
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			if(!body.contains("alert") && response.getStatusLine().getStatusCode()==200){
				String pointStr = ezwelEncrytion.decode(body);
				
				point=new BigDecimal(pointStr.replaceAll("[^0-9]",""));
			}
			} catch (Exception e) {
				LOGGER.error("ERROR"+e);
			}
			return point;
		}
		
		//이지웰 주문 요청
		public HashMap<String, Object> ezwelPointUse(HashMap<String, Object> infoMap){
			
			//ezwel 암호화 모듈
			EzwelCryptoModule ezwelEncrytion = new EzwelCryptoModule();
			
			HashMap<String, Object> resultMap=new HashMap<>();
			
			try {
				
				HttpPost httpPost = new HttpPost("http://newasp.ezwel.com/aspReceiver.ez");
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	
				postParams.add(new BasicNameValuePair("cspCd",EgovProperties.getProperty("SSO.ezwel.cspCd")));//제휴사 업체코드
				postParams.add(new BasicNameValuePair("userKey", ezwelEncrytion.encode(String.valueOf(infoMap.get("userKey")))));//유저키
				postParams.add(new BasicNameValuePair("command", ezwelEncrytion.encode("111")));//command코드
				postParams.add(new BasicNameValuePair("clientCd",ezwelEncrytion.encode(String.valueOf(infoMap.get("clientCd")))));//고객사 코드
				postParams.add(new BasicNameValuePair("usePoint",ezwelEncrytion.encode(String.valueOf(infoMap.get("usePoint")))));//선차감요청포인트
				postParams.add(new BasicNameValuePair("useMileage",ezwelEncrytion.encode(String.valueOf(infoMap.get("useMileage")))));//선차감요청적립금
				postParams.add(new BasicNameValuePair("useSpecialPoint",ezwelEncrytion.encode(String.valueOf(infoMap.get("useSpecialPoint")))));//선차감특별포인트
				postParams.add(new BasicNameValuePair("goodsNm",ezwelEncrytion.encode((String)infoMap.get("goodsNm"))));//상품 명
				postParams.add(new BasicNameValuePair("unitCost",ezwelEncrytion.encode(String.valueOf(infoMap.get("unitCost")))));//판매 단가
				postParams.add(new BasicNameValuePair("buyPrice",ezwelEncrytion.encode(String.valueOf(infoMap.get("buyPrice")))));//공급 가격
				postParams.add(new BasicNameValuePair("orderCount",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderCount")))));//주문 수량
				postParams.add(new BasicNameValuePair("orderTotal",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderTotal")))));//주문 총액
				postParams.add(new BasicNameValuePair("payMoney",ezwelEncrytion.encode(String.valueOf(infoMap.get("payMoney")))));//결제 금액
				postParams.add(new BasicNameValuePair("orderDd",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderDd")))));//주문 일
				postParams.add(new BasicNameValuePair("orderTm",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderTm")))));//주문 시간
				postParams.add(new BasicNameValuePair("orderNm",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderNm")))));//주문자 이름
				postParams.add(new BasicNameValuePair("orderEmail",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderEmail")))));//주문자 이메일
				postParams.add(new BasicNameValuePair("rcverNm",ezwelEncrytion.encode(String.valueOf(infoMap.get("rcverNm")))));////수령인이름
				postParams.add(new BasicNameValuePair("rcverMobile",ezwelEncrytion.encode(String.valueOf(infoMap.get("rcverMobile")))));//수령인 번호
				postParams.add(new BasicNameValuePair("dlvrPost",ezwelEncrytion.encode(String.valueOf(infoMap.get("dlvrPost")))));//배송지 우편번호
				postParams.add(new BasicNameValuePair("dlvrAddr1",ezwelEncrytion.encode(String.valueOf(infoMap.get("dlvrAddr1")))));//배송지 기본주소
				postParams.add(new BasicNameValuePair("dlvrAddr2",ezwelEncrytion.encode(String.valueOf(infoMap.get("dlvrAddr2")))));//배송지 상세주소
				postParams.add(new BasicNameValuePair("orderRequest",ezwelEncrytion.encode(String.valueOf(infoMap.get("orderRequest")))));//주문 요청사항
				postParams.add(new BasicNameValuePair("aspOrderNum",ezwelEncrytion.encode(String.valueOf(infoMap.get("aspOrderNum")))));//제휴사 측 확정 주문번호
				postParams.add(new BasicNameValuePair("mobileYn", ezwelEncrytion.encode((String)infoMap.get("mobileYn"))));//모바일여부
				if("Y".equals((String)infoMap.get("mobileYn"))){
					postParams.add(new BasicNameValuePair("channelType", ezwelEncrytion.encode("1002")));//주문채널
				}
				org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
				httpPost.setEntity(postEntity);
					 
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				CloseableHttpResponse response = httpClient.execute(httpPost);
					 
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				body = ezwelEncrytion.decode(body).replaceAll("[^0-9YN|]","");
				LOGGER.info("이지웰 주문요청결과"+body);
				if(body.contains("Y|") && response.getStatusLine().getStatusCode()==200){
					String ezwelOrderNo = body.substring(2,body.length());
					System.out.println("이지웰 주문번호"+ezwelOrderNo);
					resultMap.put("ezCode", ezwelOrderNo);
					//이지웰 주문 확정 요청
					postParams = new ArrayList<NameValuePair>();
					
					postParams.add(new BasicNameValuePair("cspCd",EgovProperties.getProperty("SSO.ezwel.cspCd")));//제휴사 업체코드
					postParams.add(new BasicNameValuePair("userKey", ezwelEncrytion.encode(String.valueOf(infoMap.get("userKey")))));//유저키
					postParams.add(new BasicNameValuePair("command", ezwelEncrytion.encode("112")));//command코드
					postParams.add(new BasicNameValuePair("aspOrderNum",ezwelEncrytion.encode(String.valueOf(infoMap.get("aspOrderNum")))));//제휴사 측 확정 주문번호
					postParams.add(new BasicNameValuePair("orderNum",ezwelEncrytion.encode(ezwelOrderNo)));//이지웰 주문번호

					postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
					httpPost.setEntity(postEntity);
					
					httpClient = HttpClientBuilder.create().build();
					response = httpClient.execute(httpPost);
					
					handler = new BasicResponseHandler();
					body = handler.handleResponse(response);
					body = body.replaceAll("[^YN]","");
					LOGGER.info("이지웰 주문확정결과"+body);
					if(body.equalsIgnoreCase("Y") && response.getStatusLine().getStatusCode()==200){
						resultMap.put("resultCode","R000");
						resultMap.put("resultMsg","이지웰결제성공");
						resultMap.put("success",true);
					}else{
						resultMap.put("resultCode","R019");
						resultMap.put("resultMsg","이지웰 주문확정 API - 요청 결과 오류");
						resultMap.put("success",false);
						LOGGER.error("이지웰 주문 command:112 ERROR");
						throw new RuntimeException();
					}
				}else{
					resultMap.put("resultCode","R017");
					resultMap.put("resultMsg","이지웰 선차감 API - 요청 결과 오류");
					resultMap.put("success",false);
					LOGGER.error("이지웰 주문 command:111 ERROR");
					throw new RuntimeException();
				}
			} catch (Exception e) {
				LOGGER.error("이지웰 HTTP ERROR"+e);
				e.printStackTrace();
				resultMap.put("resultCode","R018");
				resultMap.put("resultMsg","이지웰 주문확정 API - HTTP 통신 오류");
				resultMap.put("success",false);
			}
			return resultMap;
		}
		
	/*
	 * 이지웰 포인트 결제 취소
	 */
	public void cancelEzwelPointSetle(EgovMap orderSetle, OrderSetleVO setleInfo){
		EzwelCryptoModule ezwelEncrytion = new EzwelCryptoModule();
		
		try {
			System.out.println("===========================================이지웰 결제 취소 시작!");
			
			HttpPost httpPost = new HttpPost("http://newasp.ezwel.com/aspReceiver.ez");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
			System.out.println("@@" + EgovProperties.getProperty("SSO.ezwel.cspCd"));
			
			System.out.println("@@" + String.valueOf(orderSetle.get("orderNo")));
			System.out.println("@@" + String.valueOf(orderSetle.get("orderGroupNo")));
			System.out.println("@@" + String.valueOf(orderSetle.get("ezwSetleConfmNo")));
			System.out.println("@@" + String.valueOf(String.valueOf("취소 사유")));

			postParams.add(new BasicNameValuePair("cspCd",EgovProperties.getProperty("SSO.ezwel.cspCd")));//제휴사 업체코드
			postParams.add(new BasicNameValuePair("command", ezwelEncrytion.encode("102")));//주문/결제 취소 요청
			postParams.add(new BasicNameValuePair("aspOrderNum",ezwelEncrytion.encode(String.valueOf(orderSetle.get("orderNo")))));//제휴사 측 확정 주문번호
			postParams.add(new BasicNameValuePair("orderNum",ezwelEncrytion.encode(String.valueOf(orderSetle.get("ezwSetleConfmNo")))));
			postParams.add(new BasicNameValuePair("cancelRsn",ezwelEncrytion.encode(String.valueOf("취소 사유"))));//cancelRsn
			postParams.add(new BasicNameValuePair("mobileYn",ezwelEncrytion.encode(String.valueOf(orderSetle.get("mobileYn")))));

			org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			httpPost.setEntity(postEntity);
				 
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
				 
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			System.out.println("body:" + body);
			
			body = body.replaceAll("[^YN]","");
			if (body.equalsIgnoreCase("Y") && response.getStatusLine().getStatusCode()==200){
				setleInfo.setSetleResultCode("R000");
				setleInfo.setSetleSttusCode("C");
				setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 성공");
				System.out.println("======================================이지웰 취소성공");
			} else{
				setleInfo.setSetleResultCode("R019");
				setleInfo.setSetleSttusCode("F");
				setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 실패 - 요청 결과 오류");
				System.out.println("======================================이지웰 취소실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			setleInfo.setSetleResultCode("R018");
			setleInfo.setSetleSttusCode("F");
			setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 실패 - HTTP 통신 오류");
			System.out.println("======================================이지웰 취소실패");
		}

	}
	
	/*
	 * 이지웰 포인트 결제 부분 취소
	 */
	public void partialEzwelPointSetle(EgovMap orderSetle, OrderSetleVO setleInfo){
		EzwelCryptoModule ezwelEncrytion = new EzwelCryptoModule();
		
		try {
			
			System.out.println("===========================================이지웰 결제 부분취소 시작!");
			
			HttpPost httpPost = new HttpPost("http://newasp.ezwel.com/aspReceiver.ez");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	
			postParams.add(new BasicNameValuePair("cspCd",EgovProperties.getProperty("SSO.ezwel.cspCd")));//제휴사 업체코드
			postParams.add(new BasicNameValuePair("command", ezwelEncrytion.encode("118")));//주문/결제 취소 요청
			postParams.add(new BasicNameValuePair("clientCd",ezwelEncrytion.encode(String.valueOf(orderSetle.get("clientCd"))))); //*
			postParams.add(new BasicNameValuePair("usePoint", ezwelEncrytion.encode(String.valueOf(orderSetle.get("usePoint")))));//재결제 요청 포인트
			postParams.add(new BasicNameValuePair("goodsNm", ezwelEncrytion.encode(String.valueOf(orderSetle.get("goodsNm") + "[부분취소]"))));//상품명			
			postParams.add(new BasicNameValuePair("unitCost", ezwelEncrytion.encode(String.valueOf(orderSetle.get("goodsPc"))))); // 판매 단가, 금액정보합계 *
			postParams.add(new BasicNameValuePair("buyPrice", ezwelEncrytion.encode(String.valueOf(orderSetle.get("buyPrice"))))); // 공급가격
			postParams.add(new BasicNameValuePair("orderCount", ezwelEncrytion.encode(String.valueOf(orderSetle.get("orderCo"))))); // 주문수량 *
			postParams.add(new BasicNameValuePair("orderTotal", ezwelEncrytion.encode(String.valueOf(orderSetle.get("totAmount"))))); // 주문총액 *
			postParams.add(new BasicNameValuePair("payMoney", ezwelEncrytion.encode(String.valueOf(orderSetle.get("setlePoint"))))); // 결제금액 *
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();

			postParams.add(new BasicNameValuePair("orderDd", ezwelEncrytion.encode(String.valueOf(sdf.format(date))))); // 주문일 yyyy-mm-dd
			postParams.add(new BasicNameValuePair("orderTm", ezwelEncrytion.encode(String.valueOf(tf.format(date))))); // 주문시간 hh:mm:ss
			postParams.add(new BasicNameValuePair("orderNm", ezwelEncrytion.encode(String.valueOf(orderSetle.get("mberNm"))))); // 주문자 이름
			postParams.add(new BasicNameValuePair("userKey", ezwelEncrytion.encode(String.valueOf(orderSetle.get("userKey"))))); // 유저키
			postParams.add(new BasicNameValuePair("orderEmail", ezwelEncrytion.encode(String.valueOf(orderSetle.get("email"))))); // 주문자 이메일
			postParams.add(new BasicNameValuePair("aspOrderNum",ezwelEncrytion.encode(String.valueOf(orderSetle.get("orderGroupNo")))));//제휴사 측 확정 주문번호
			postParams.add(new BasicNameValuePair("orderNum",ezwelEncrytion.encode(String.valueOf(orderSetle.get("ezwSetleConfmNo")))));
			
			System.out.println("@@@@@@@@@@@@@@@@" + postParams.toString());
			
			System.out.println("@@@@@@@@@@@@@@@@" + "clientCd" + String.valueOf(orderSetle.get("clientCd")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "usePoint" + String.valueOf(orderSetle.get("usePoint")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "goodsNm" + String.valueOf(orderSetle.get("goodsNm") + "[부분취소]") );
			System.out.println("@@@@@@@@@@@@@@@@" + "unitCost" + String.valueOf(orderSetle.get("unitCost")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "buyPrice" + String.valueOf(orderSetle.get("buyPrice")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderCount" + String.valueOf(orderSetle.get("orderCo")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderTotal" + String.valueOf(orderSetle.get("setlePoint")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "payMoney" + String.valueOf(orderSetle.get("setlePoint")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderDd" + String.valueOf(sdf.format(date)) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderTm" + String.valueOf(tf.format(date)) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderNm" + String.valueOf(orderSetle.get("mberNm")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "userKey" + String.valueOf(orderSetle.get("userKey")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderEmail" + String.valueOf(orderSetle.get("email")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "aspOrderNum" + String.valueOf(orderSetle.get("orderGroupNo")) );
			System.out.println("@@@@@@@@@@@@@@@@" + "orderNum" + String.valueOf(orderSetle.get("ezwSetleConfmNo")));


			org.apache.http.HttpEntity postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			httpPost.setEntity(postEntity);
				 
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
				 
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			body = ezwelEncrytion.decode(body).replaceAll("[^0-9YN|]","");
			LOGGER.info("이지웰 주문요청결과"+body);
			if(body.contains("Y|") && response.getStatusLine().getStatusCode()==200){
				setleInfo.setSetleResultCode("R000");
				setleInfo.setSetleSttusCode("C");
				setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 성공");
				System.out.println("======================================이지웰 취소성공");
			} else{
				setleInfo.setSetleResultCode("R019");
				setleInfo.setSetleSttusCode("F");
				setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 실패 - 요청 결과 오류");
				System.out.println("======================================이지웰 취소실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			setleInfo.setSetleResultCode("R018");
			setleInfo.setSetleSttusCode("F");
			setleInfo.setSetleResultMssage("이지웰 포인트 결제 취소 실패 - HTTP 통신 오류");
			System.out.println("======================================이지웰 취소실패");
		}

	}
	/*
	 * 이지웰 헤더 부분 
	 */
	public String ezwelHeader(MberVO mb, String devcie){
		
		String result = "";
		
		try {
			
			System.out.println("===========================================헤더요청!");
			byte[] decodeByte = org.apache.xerces.impl.dv.util.Base64.decode(EgovProperties.getProperty("SSO.ezwel.cspCd"));
			String deCspCd = new String(decodeByte,"UTF-8");
			String token = mb.getClientCd()+"."+devcie+"."+deCspCd+"."+mb.getAuthKey();
			java.net.URI uri = new java.net.URI("http://support.ezwel.com/authsupport/headerJson.ez");
			token = token.replaceAll("\r","").replaceAll("\n", ""); 
			uri = new URIBuilder(uri).addParameter("token", token).build();
			HttpGet httpGet = new HttpGet(uri);
			httpGet.addHeader("Content-type","application/json");
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			
			LOGGER.info("이지웰 헤더요청결과"+body);
			System.out.println((response.getStatusLine().getStatusCode()));
			
			if(200 == response.getStatusLine().getStatusCode()){
				com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
				JsonElement jObject = parser.parse(body.toString());
				result = jObject.getAsJsonObject().get("header").getAsString();	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======================================이지웰 헤더 통신 실패");
		}
		return result;
	}

}

package modoo.module.mber.sso.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.cmm.service.EgovProperties;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import modoo.module.mber.sso.service.EzwelCrypto;;



public class EzwelCryptoModule{
	
	//private String key = "ez!1234567891011";
	private String key = EgovProperties.getProperty("SSO.ezwel.key");
	
	private String getKey() {
		return key;
	}
	private void setKey(String cspCd){

		this.key = cspCd;
	}
	
	public String encode(String strEncode) throws UnsupportedEncodingException {
		
		
		EzwelCrypto ezwelCrypto = new EzwelCrypto();
		BASE64Encoder encoder = new BASE64Encoder();
		
		String encryptText = "";

		if(!strEncode.equals("") && strEncode != null){
			encryptText = encoder.encode(ezwelCrypto.encrypt(strEncode, getKey().getBytes(), "UTF-8"));
		}
		return encryptText;
	}
	public String decode(String strDecode) throws IOException{
		
		EzwelCrypto ezwelCrypto = new EzwelCrypto();
		BASE64Decoder decoder = new BASE64Decoder();
		
		String decryptText = "";
			//if(!strDecode.equals("") && strDecode != null){
			if(StringUtils.isNotEmpty(strDecode)){
				byte[] encryptbytes = decoder.decodeBuffer(strDecode);
				decryptText = ezwelCrypto.decryptAsString(encryptbytes, getKey().getBytes(), "UTF-8");
			}
		
		return decryptText;
	}
	
}
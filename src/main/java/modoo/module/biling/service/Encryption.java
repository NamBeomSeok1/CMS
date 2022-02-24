package modoo.module.biling.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import egovframework.com.cmm.service.EgovProperties;

public class Encryption {
	
private static String iv=EgovProperties.getProperty("INICIS.subscription.iv");
private static String key=EgovProperties.getProperty("INICIS.subscription.key");
public String getKey(){
	return key;
}

public void setKeyIv(String key,String iv){
	this.iv=iv;
	this.key=key;
}

public HashMap<String,String> encryption(List<String> enList, String[] enListName) throws Exception{
		
		HashMap<String,String> res = new HashMap<>();
		byte[] data = key.getBytes();
		
		SecretKey skey = new SecretKeySpec(data, "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cipher.init(Cipher.ENCRYPT_MODE, skey, new IvParameterSpec(iv.getBytes()));

			for(int i=0;i<enList.size();i++){
			byte[] encrypted = cipher.doFinal(enList.get(i).getBytes("UTF-8"));
				String enStr= new String(Base64.encodeBase64(encrypted),StandardCharsets.UTF_8);
				res.put(enListName[i], enStr);
			}
			return res;
	}

public String decryption(String deStr) throws Exception{
	
	byte[] data = key.getBytes();
		SecretKey skey = new SecretKeySpec(data, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv.getBytes("UTF-8")));
	    byte[] byteStr = Base64.decodeBase64(deStr.getBytes("UTF-8"));
	    String decStr = new String(cipher.doFinal(byteStr), "UTF-8");
	
	return decStr;
}
	/*암호화 끝*/
	
	/*Hshkey 발급 Method*/
	/*public String hashKey(String inputVal) throws Exception {

		MessageDigest md =MessageDigest.getInstance("SHA-512");
		byte[] messageDigest = md.digest(inputVal.getBytes());			
		BigInteger no = new BigInteger(1, messageDigest);
		String hashval = no.toString(16);
	
		while(hashval.length()<32){
			hashval = "0"+ hashval;
		}
		return hashval;
	}*/

	/*Hshkey 발급 Method*/
	public String hashKey(String inputVal) throws Exception {
		
		String toReturn = null;
		try {
		    MessageDigest digest = MessageDigest.getInstance("SHA-512");
		    digest.reset();
		    digest.update(inputVal.getBytes("utf8"));
		    toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return toReturn;
	}
	/*Hshkey 끝*/
	
}

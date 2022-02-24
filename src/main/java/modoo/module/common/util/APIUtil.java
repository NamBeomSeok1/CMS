package modoo.module.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class APIUtil {

	//GET방식
	public static String getUrlStream(String url){
		String returnStr = "";
		java.net.URL serviceUrl;
		try {
			serviceUrl = new java.net.URL(url);
			
			StringBuffer bf = new StringBuffer();
			BufferedReader in2 = null;
			URLConnection conn2 = null;
			bf = new StringBuffer();
			try {
				conn2 = serviceUrl.openConnection();
				conn2.setConnectTimeout(3000);
				//conn2.setReadTimeout(5000);
				conn2.setReadTimeout(15000);
				in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
				String bufLine = null;
				while( (bufLine = in2.readLine()) != null) {
					bf.append(bufLine.trim());
				}
			} catch (IOException e) {
				//e.printStackTrace();
			} finally {
				if (in2 != null) {
					try {
						in2.close();
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
				returnStr = bf.toString();
			}
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		}
		
		return returnStr;
	}
		
	//POST방식
	public static String postUrlStream(URL url, String param){
		String returnStr = "";
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			conn.setConnectTimeout(0);
			//conn.setReadTimeout(1000*5);
			conn.setReadTimeout(1000*15);
			conn.connect();
			
			OutputStreamWriter out;
			try {
				out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				out.write(param);
				out.flush();
				out.close();
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				String bufLine;
				StringBuffer bf = new StringBuffer();
				while((bufLine = rd.readLine()) != null){
					bf.append(bufLine.trim());
				}
				rd.close();
				returnStr = bf.toString();
				
			} catch (UnsupportedEncodingException e) {
				//e.printStackTrace();
				//returnStr = "outE";
			} catch (IOException e) {
				//e.printStackTrace();
				//returnStr = "outE";
			} finally{
				conn.disconnect();
			}
		} catch (IOException e1) {
			//e1.printStackTrace();
			//returnStr = "conE";
		} 
		
		return returnStr;
	}
	
	//POST방식(body, json값으로 파라미터 전송)
	public static String postUrlBodyJson(URL url, String param){
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		  try{
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
		      
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes("UTF-8"));
			os.flush();
		    os.close();
		    
			//리턴된 결과 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
		    
			conn.disconnect();
		  }catch(Exception e){
		      //e.printStackTrace();
			  
		  }	
		  
		  return outResult.toString();
	}
}

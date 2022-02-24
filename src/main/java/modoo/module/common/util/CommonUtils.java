package modoo.module.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {

	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Edg") > -1) {
			return "Edge";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	public static String getMobile(HttpServletRequest request) {
		String header = request.getHeader("User-Agent").toUpperCase();
		if(header.indexOf("MODOO") > -1 || header.indexOf("MOBILE") > -1 || header.indexOf("MRA58N") > -1 ||header.indexOf("LRX21T") > -1) {
			if(header.indexOf("PHONE") == -1){
				return "Y";
			}
			else{
				return "Y";
			}
		}else{
			return "N";
		}
	}
	public static String getMobile() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return getMobile(req);
	}
	
	public static String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}
		
		String ret = data;
		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
		ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
		ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
		ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		return ret;
	}
	
	/**
	 * 입력된 날짜 시간 문자열 확인 후 14자리 리턴
	 * Format : YYYY-MM-DD HH:mm:ss -> YYYYMMDDHHmmss
	 * @param dateTimeStr
	 * @return
	 */
	public static String validChkDateTime(String dateTimeStr) {
		if(dateTimeStr == null) {
			throw new NullPointerException("dateTimeStr argument can not be null ");
		}
		
		dateTimeStr = dateTimeStr.replaceAll("[^0-9]","");
		if(dateTimeStr.length() != 14) {
			throw new IllegalArgumentException("Invalid date time format: " + dateTimeStr);
		}
		
		return dateTimeStr;
	}
	
	/**
	 * 현재시간 : YYYYMMDDHHmmss
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 오늘날짜 반환
	 * @param formatStr
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateFormat(String formatStr) throws Exception {
		return getCurrentDateFormat(formatStr, 0);
	}
	/**
	 * 현재날짜 기준 날짜반환
	 * @param formatStr
	 * @param addDay
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateFormat(String formatStr, int addDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if(addDay != 0) {
			cal.add(Calendar.DATE, addDay);
		}
		return (new SimpleDateFormat(formatStr).format(cal.getTime()));
	}
	
	public static String getCurrentDateFormat(String formatStr, String addType, String daySe, int value) {
		Calendar cal = Calendar.getInstance();
		String result = "";
		if(value != 0) {
			if("DAY".equals(addType)) {
				cal.add(Calendar.DATE, value);
			}else if("MONTH".equals(addType)) {
				cal.add(Calendar.MONTH, value);
			}else if("YEAR".equals(addType)) {
				cal.add(Calendar.YEAR, value);
			}
		}
		
		if(StringUtils.isEmpty(daySe)) {
		}else if("F".equals(daySe)) { //첫날
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}else if("L".equals(daySe)) { //마지막날
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
		}
		result =  (new SimpleDateFormat(formatStr).format(cal.getTime()));
		
		return result;
	}
	
	/**
	 * 주 계산된 날짜 변환
	 * @param formatStr
	 * @param wkNm
	 * @param week
	 * @return
	 */
	public static String getWeekDay(String formatStr, String wkNm, int week) {
		Calendar cal = Calendar.getInstance();
		String result = "";
		
		int value = 0;
		if("MON".equals(wkNm)) {
			value = week * 7;
			cal.add(Calendar.DATE, value);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}else if("SUN".equals(wkNm)) {
			value = (week+1) * 7;
			cal.add(Calendar.DATE, value);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		
		result =  (new SimpleDateFormat(formatStr).format(cal.getTime()));
		
		return result;
	}
}

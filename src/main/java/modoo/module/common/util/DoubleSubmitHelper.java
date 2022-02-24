package modoo.module.common.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.util.EgovDoubleSubmitHelper;

public class DoubleSubmitHelper extends EgovDoubleSubmitHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(DoubleSubmitHelper.class);

	
	/**
	 * MultipartRequest 토큰검사
	 * @param multiRequest
	 * @return
	 */
	public static boolean checkAndSaveToken(MultipartHttpServletRequest multiRequest) {
		HttpSession session = multiRequest.getSession();
		String tokenKey = DEFAULT_TOKEN_KEY;
		
		if (session.getAttribute(EgovDoubleSubmitHelper.SESSION_TOKEN_KEY) == null) {
			throw new RuntimeException("Double Submit Preventer TagLig isn't set. Check JSP.");
		}
		
		String parameter = multiRequest.getParameter(EgovDoubleSubmitHelper.PARAMETER_NAME);
		
		// check parameter
		if (parameter == null) {
			throw new RuntimeException("Double Submit Preventer parameter isn't set. Check JSP.");
		}

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) session.getAttribute(EgovDoubleSubmitHelper.SESSION_TOKEN_KEY);
		
		if (parameter.equals(map.get(tokenKey))) {
			LOGGER.debug("[Double Submit] session token ({}) equals to parameter token.", tokenKey);
			map.put(tokenKey, getNewUUID());
			return true;
		}

		LOGGER.debug("[Double Submit] session token ({}) isn't equal to parameter token.", tokenKey);
		
		return false;
	}
}

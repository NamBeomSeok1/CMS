package modoo.module.system.log.login.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import modoo.module.common.util.SiteDomainHelper;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

public class LoginLogAspect {

	@Resource(name="loginLogService")
	private LoginLogService loginLogService;
	
	@Resource(name = "loginLogIdGnrService")
	private EgovIdGnrService loginLogIdGnrService;
	
	/**
	 * 로그인 정보 저장
	 * @throws Throwable
	 */
	public void logLogin() throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String siteId = SiteDomainHelper.getSiteId();
		String uniqId = "";
		String ip = "";

		/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    		LoginVO user = (LoginVO) request.getSession().getAttribute("loginVO");
			uniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
			ip = (user == null || user.getIp() == null) ? "" : user.getIp();
    	}

    	LoginLogVO loginLog = new LoginLogVO();
    	loginLog.setLogId(loginLogIdGnrService.getNextStringId());
    	loginLog.setSiteId(siteId);
    	loginLog.setConectId(uniqId);
        loginLog.setConectIp(ip);
        loginLog.setConectMthd("I"); // 로그인:I, 로그아웃:O
        loginLog.setErrorOccrrncAt("N");
        loginLog.setErrorCode("");
        loginLogService.insertLoginLog(loginLog);

	}
	
	/**
	 * 로그아웃 정보 저장
	 * @throws Throwable
	 */
	public void logLogout() throws Throwable {
		String siteId = SiteDomainHelper.getSiteId();
		String uniqId = "";
		String ip = "";

		/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
			ip = (user == null || user.getIp() == null) ? "" : user.getIp();
    	}

    	LoginLogVO loginLog = new LoginLogVO();
    	loginLog.setLogId(loginLogIdGnrService.getNextStringId());
    	loginLog.setSiteId(siteId);
    	loginLog.setConectId(uniqId);
        loginLog.setConectIp(ip);
        loginLog.setConectMthd("O");
        loginLog.setErrorOccrrncAt("N");
        loginLog.setErrorCode("");
        loginLogService.insertLoginLog(loginLog);
	}
	
	
}

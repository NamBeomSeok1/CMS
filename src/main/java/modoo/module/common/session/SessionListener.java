package modoo.module.common.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import modoo.module.common.util.SiteDomainHelper;
import modoo.module.system.log.conect.service.ConectLogService;
import modoo.module.system.log.conect.service.ConectLogVO;

public class SessionListener implements HttpSessionListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		session.setAttribute("remoteAddr", req.getRemoteAddr());
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(se.getSession().getServletContext());
		LOGGER.debug("session created " + session.getId());
		ConectLogService conectLogService = (ConectLogService)context.getBean("conectLogService");
		
		try {
			String siteId = SiteDomainHelper.getSiteId();
			String agent = req.getHeader("user-agent");
			String referer = req.getHeader("referer");
			if(siteId != null) {
				ConectLogVO conectLog = new ConectLogVO();
				session.setAttribute("siteId", siteId);
				conectLog.setSiteId(siteId);
				conectLog.setSessionId(session.getId());
				conectLog.setLogSeCode("CREATE"); //생성
				conectLog.setUserAgent(agent);
				conectLog.setReferer(referer);
				conectLog.setRqesterIp(req.getRemoteAddr());
				
				conectLogService.insertConectLog(conectLog);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.error("Exception : " + e);
		}
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();

		try {
			if(session.getAttribute("loginVO") != null) {
				HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				String remoteAddr = (String)session.getAttribute("remoteAddr");
			
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(se.getSession().getServletContext());
				LOGGER.debug("session destroyed " + session.getId());
				ConectLogService conectLogService = (ConectLogService)context.getBean("conectLogService");

				String siteId = (String)session.getAttribute("siteId"); //SiteDomainHelper.getSiteId();
				String agent = req.getHeader("user-agent");
				String referer = req.getHeader("referer");
				if(siteId != null) {
					ConectLogVO conectLog = new ConectLogVO();
					conectLog.setSiteId(siteId);
					conectLog.setSessionId(session.getId());
					conectLog.setLogSeCode("DESTROY"); //소멸
					conectLog.setUserAgent(agent);
					conectLog.setReferer(referer);
					conectLog.setRqesterIp(remoteAddr);
					conectLogService.insertConectLog(conectLog);
				}
				
			}
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.error("Exception : " + e);
		}
	}

}

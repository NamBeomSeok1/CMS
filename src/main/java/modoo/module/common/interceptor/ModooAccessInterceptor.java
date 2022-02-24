package modoo.module.common.interceptor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.rte.fdl.access.bean.AuthorityResourceMetadata;
import egovframework.rte.fdl.access.config.EgovAccessConfigShare;
import egovframework.rte.fdl.access.interceptor.EgovAccessUtil;
import modoo.module.common.service.ModooUserDetailHelper;

public class ModooAccessInterceptor extends HandlerInterceptorAdapter implements ApplicationContextAware {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModooAccessInterceptor.class);
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("##### ModooAccessInterceptor Start #####");

        // 인증 체크
        boolean isAuthenticated = ModooUserDetailHelper.isAuthenticated();
        LOGGER.debug("##### ModooAccessInterceptor interceptor isAuthenticated >>> {}", isAuthenticated);
        if (!isAuthenticated) {
            response.sendRedirect(request.getContextPath() + EgovAccessConfigShare.DEF_LOGIN_URL);
            return false;
        }

        String requestMatchType = EgovAccessConfigShare.DEF_REQUEST_MATCH_TYPE;
        String url = request.getRequestURI().replace(request.getContextPath(),"");
        boolean matchStatus = false;

        // 권한 체크
        List<String> authorityList = ModooUserDetailHelper.getAuthorities();
        LOGGER.debug("##### ModooAccessInterceptor authorityList : {} #####", authorityList);
        String authority = "";
        for (String str : authorityList) {
            authority = str;
        }

        // 권한별 접근 제한
        AuthorityResourceMetadata authorityResourceMetadata = context.getBean(AuthorityResourceMetadata.class);
        List<Map<String, Object>> list = authorityResourceMetadata.getResourceMap();
        Iterator<Map<String, Object>> iterator = list.iterator();
        Map<String, Object> tempMap;
        while (iterator.hasNext()) {
            tempMap = iterator.next();
            if (authority.equals(tempMap.get("authority"))) {
                // Ant Style Path Check
                if ("ant".equals(requestMatchType)) {
                    LOGGER.debug("##### ModooAccessInterceptor ant pattern #####");
                    matchStatus = EgovAccessUtil.antMatcher((String) tempMap.get("url"), url);
                    LOGGER.debug("##### ModooAccessInterceptor ant pattern : {} , url : {}, match : {} #####", tempMap.get("url"), url, matchStatus);
                }
                // Regular Expression Style Path Check
                else {
                    LOGGER.debug("##### ModooAccessInterceptor regex pattern #####");
                    matchStatus = EgovAccessUtil.regexMatcher((String) tempMap.get("url"), url);
                    LOGGER.debug("##### EgovAccessConfigTest regex pattern : {} , url : {}, match : {} #####", tempMap.get("url"), url, matchStatus);
                }
                if (matchStatus) {
                    return true;
                }
            }
        }

        // 허가되지 않은 경우 접근 제한
        if (!matchStatus) {
            response.sendRedirect(request.getContextPath() + EgovAccessConfigShare.DEF_ACCESS_DENIED_URL);
            return false;
        }

        return true;
	}

}

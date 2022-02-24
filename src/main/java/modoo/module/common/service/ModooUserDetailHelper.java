package modoo.module.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import egovframework.rte.fdl.access.bean.AuthorityResourceMetadata;
import egovframework.rte.fdl.access.service.EgovAccessService;

public class ModooUserDetailHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModooUserDetailHelper.class);
	
	//@Resource(name = "mberService")
	private static MberService mberService;
	
	private EgovAccessService egovAccessService;
	
	public static MberService getMberService() {return mberService;}
	
	public void setMberService(MberService mberService) {
		ModooUserDetailHelper.mberService = mberService;
	}
	
	public static Object getAuthenticatedUser() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		}
		return RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
	}
	
	public static List<String> getAuthorities() {
		List<String> list = new ArrayList<String>();
		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		} else {
			String accessUser = (String) RequestContextHolder.getRequestAttributes().getAttribute("accessUser", RequestAttributes.SCOPE_SESSION);
			//List<Map<String, Object>> listmap = AuthorityResourceMetadata.getAuthorityList();
			
			if(accessUser != null) {
				try {
					List<MberVO> mberAuthList = mberService.selectMberAuthList();
					for(MberVO vo : mberAuthList) {
						if(vo.getMberId().equals(accessUser)) {
							String roleNm = vo.getAuthorCode();
							list.add(roleNm);

							if(roleNm.equals("ROLE_ADMIN")) {
								list.add("ROLE_EMPLOYEE");
							}
							
							if(roleNm.equals("ROLE_EMPLOYEE") || roleNm.equals("ROLE_ADMIN")) {
								list.add("ROLE_SHOP");
								list.add("ROLE_USER");
							}
							
							/*list.add("IS_AUTHENTICATED_FULLY");
							list.add("IS_AUTHENTICATED_REMEMBERED");
							list.add("IS_AUTHENTICATED_ANONYMOUSLY");
							list.add("ROLE_ANONYMOUS");*/
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			list.add("IS_AUTHENTICATED_FULLY");
			list.add("IS_AUTHENTICATED_REMEMBERED");
			list.add("IS_AUTHENTICATED_ANONYMOUSLY");
			list.add("ROLE_ANONYMOUS");
			
			/*
			List<Map<String, Object>> listmap = AuthorityResourceMetadata.getAuthorityList();
			if (!StringUtils.isEmpty(accessUser) && !ObjectUtils.isEmpty(listmap)) {
				Iterator<Map<String, Object>> iterator = listmap.iterator();
				Map<String, Object> tempMap;
				while (iterator.hasNext()) {
					tempMap = iterator.next();
					if (accessUser.equals(tempMap.get("userid"))) {
						String roleNm = (String) tempMap.get("authority");
						list.add(roleNm);
						
						//TODO : session방식일때 상속받은 권한을 모두 못갖고 와서 다음처럼 구현함. 추후 방법을 찾아 수정이 필요. by dy.moon 
						
						if(roleNm.equals("ROLE_ADMIN")) {
							list.add("ROLE_EMPLOYEE");
						}
						
						if(roleNm.equals("ROLE_EMPLOYEE") || roleNm.equals("ROLE_ADMIN")) {
							list.add("ROLE_SHOP");
							list.add("ROLE_USER");
						}
						
						list.add("IS_AUTHENTICATED_FULLY");
						list.add("IS_AUTHENTICATED_REMEMBERED");
						list.add("IS_AUTHENTICATED_ANONYMOUSLY");
						list.add("ROLE_ANONYMOUS");
					}
				}
			}
			*/
			return list;
		}
	}
	

	public static Boolean isAuthenticated() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return false;
		} else {
			if (RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION) == null) {
				return false;
			} else {
				return true;
			}
		}
	}
}

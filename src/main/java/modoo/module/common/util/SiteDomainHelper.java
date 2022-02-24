package modoo.module.common.util;

import modoo.module.site.service.SiteDomainService;

public class SiteDomainHelper {
	
	static SiteDomainService siteDomainService;
	
	public SiteDomainService getSiteDomainService() { return siteDomainService; }
	
	public void setSiteDomainService(SiteDomainService siteDomainService) {
		SiteDomainHelper.siteDomainService = siteDomainService;
	}
	
	public static String getDomain() {
		return siteDomainService.getDomainNm();
	}
	
	/**
	 * 사이트고유ID
	 * @return
	 * @throws Exception
	 */
	public static String getSiteId() throws Exception {
		return siteDomainService.getSiteId();
	}

}

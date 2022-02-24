package modoo.module.site.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import modoo.module.site.service.SiteDomainService;
import modoo.module.site.service.SiteDomainVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("siteDomainService")
public class SiteDomainServiceImpl extends EgovAbstractServiceImpl implements SiteDomainService {
	
	@Resource(name = "siteDomainMapper")
	private SiteDomainMapper siteDomainMapper;
	
	private HashMap<String, SiteDomainVO> siteDomainMap = new HashMap<String, SiteDomainVO>();
	
	/**
	 * 도메인 명
	 */
	@Override
	public String getDomainNm() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getServerName();
	}

	/**
	 * 사이트 도메인 목록
	 */
	@Override
	public List<SiteDomainVO> selectSiteDomainList(SiteDomainVO searchVO) throws Exception {
		return siteDomainMapper.selectSiteDomainList(searchVO);
	}

	/**
	 * 사이트 도메인 고유ID
	 */
	@Override
	public String getSiteId() throws Exception {
		String siteId = "";

		SiteDomainVO siteDomain = new SiteDomainVO();
		if(this.siteDomainMap.containsKey(this.getDomainNm())) {
			siteDomain = this.siteDomainMap.get(this.getDomainNm());
			siteId = siteDomain.getSiteId();
		}else {
			siteDomain.setDomainNm(this.getDomainNm());
			siteDomain = siteDomainMapper.selectSiteDomain(siteDomain);

			if(siteDomain != null) {
				this.siteDomainMap.put(this.getDomainNm(), siteDomain);
				siteId = siteDomain.getSiteId();
			}
		}

		return siteId;
	}

}

package modoo.module.site.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import modoo.module.site.service.SiteDomainVO;
import modoo.module.site.service.SiteService;
import modoo.module.site.service.SiteVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("siteService")
public class SiteServiceImpl extends EgovAbstractServiceImpl implements SiteService {
	
	private final static int MAX_DOMAIN_CNT = 3;
	
	@Resource(name = "siteMapper")
	private SiteMapper siteMapper;
	
	@Resource(name = "siteDomainMapper")
	private SiteDomainMapper siteDomainMapper;
	
	private List<SiteVO> siteList = new ArrayList<SiteVO>(); 

	/**
	 * 사이트 목록
	 */
	@Override
	public List<SiteVO> selectSiteList(SiteVO searchVO) throws Exception {
		return siteMapper.selectSiteList(searchVO);
	}

	/**
	 * 사이트 목록 카운트
	 */
	@Override
	public int selectSiteListCnt(SiteVO searchVO) throws Exception {
		return siteMapper.selectSiteListCnt(searchVO);
	}

	/**
	 * 사이트 저장
	 */
	@Override
	public void insertSite(SiteVO site) throws Exception {
		siteMapper.insertSite(site);
	}

	/**
	 * 사이트 상세
	 */
	@Override
	public SiteVO selectSite(SiteVO site) throws Exception {
		SiteVO vo = siteMapper.selectSite(site);
		//도메인 목록
		SiteDomainVO siteDomain = new SiteDomainVO();
		siteDomain.setSiteId(site.getSiteId());
		List<SiteDomainVO> siteDomainList = siteDomainMapper.selectSiteDomainList(siteDomain);
		vo.setSiteDomainList(siteDomainList);

		if(vo.getSiteDomainList().size() < MAX_DOMAIN_CNT) {
			vo.initSiteDoaminList(MAX_DOMAIN_CNT);
		}
		return vo;

	}

	/**
	 * 사이트 수정
	 */
	@Override
	public void updateSite(SiteVO site) throws Exception {
		//기존 도메인 삭제
		SiteDomainVO siteDomain = new SiteDomainVO();
		siteDomain.setSiteId(site.getSiteId());
		siteDomainMapper.deleteAllSiteDomain(siteDomain);

		List<SiteDomainVO> siteDomainList = site.getSiteDomainList();
		int index = 1;
		for(SiteDomainVO vo : siteDomainList) {
			if(StringUtils.isNotEmpty(vo.getDomainNm())) {
				vo.setSiteId(site.getSiteId());
				vo.setDomainSn(index++);
				siteDomainMapper.insertSiteDomain(vo);
			}
		}

		siteMapper.updateSite(site);
	}

	/**
	 * 사이트 삭제
	 */
	@Override
	public void deleteSite(SiteVO site) throws Exception {
		siteMapper.deleteSite(site);
	}
	
	/**
	 * 사이트 상세 정보
	 */
	@Override
	public SiteVO getSiteCashedInfo(String siteId) throws Exception {

		SiteVO site = new SiteVO();
		if(siteList.size() == 0) {
			SiteVO searchVO = new SiteVO();
			this.siteList = siteMapper.selectSiteList(searchVO);
		}
		
		for(SiteVO vo : this.siteList) {
			if(vo.getSiteId().equals(siteId)) {
				site = vo;
			}
		}
		
		return site;
	}

}

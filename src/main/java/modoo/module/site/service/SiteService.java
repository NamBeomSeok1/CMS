package modoo.module.site.service;

import java.util.List;

public interface SiteService {
	
	/**
	 * 사이트 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<SiteVO> selectSiteList(SiteVO searchVO) throws Exception;
	
	/**
	 * 사이트 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectSiteListCnt(SiteVO searchVO) throws Exception;
	
	/**
	 * 사이트 저장
	 * @param site
	 * @throws Exception
	 */
	void insertSite(SiteVO site) throws Exception;
	
	/**
	 * 사이트 상세
	 * @param site
	 * @return
	 * @throws Exception
	 */
	SiteVO selectSite(SiteVO site) throws Exception;
	
	/**
	 * 사이트 수정
	 * @param site
	 * @throws Exception
	 */
	void updateSite(SiteVO site) throws Exception;
	
	/**
	 * 사이트 삭제
	 * @param site
	 * @throws Exception
	 */
	void deleteSite(SiteVO site) throws Exception;

	/**
	 * 사이트 상세 정보
	 * @param site
	 * @return
	 * @throws Exception
	 */
	SiteVO getSiteCashedInfo(String siteId) throws Exception;

}

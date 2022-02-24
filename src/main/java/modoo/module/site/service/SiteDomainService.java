package modoo.module.site.service;

import java.util.List;

public interface SiteDomainService {
	
	/**
	 * 도메인명
	 * @return
	 * @throws Exception
	 */
	String getDomainNm();

	/**
	 * 사이트 도메인 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<SiteDomainVO> selectSiteDomainList(SiteDomainVO searchVO) throws Exception;
	
	/**
	 * 사이트 도메인 고유ID
	 * @return
	 * @throws Exception
	 */
	String getSiteId() throws Exception;

	
}

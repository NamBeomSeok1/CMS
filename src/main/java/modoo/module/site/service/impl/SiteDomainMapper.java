package modoo.module.site.service.impl;

import java.util.List;

import modoo.module.site.service.SiteDomainVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface SiteDomainMapper {

	/**
	 * 사이트 도메인 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<SiteDomainVO> selectSiteDomainList(SiteDomainVO searchVO) throws Exception;
	
	/**
	 * 사이트 도메인 저장
	 * @param siteDomain
	 * @throws Exception
	 */
	void insertSiteDomain(SiteDomainVO siteDomain) throws Exception;
	
	/**
	 * 사이트 모든 도메인 삭제
	 * @param siteDomain
	 * @throws Exception
	 */
	void deleteAllSiteDomain(SiteDomainVO siteDomain) throws Exception;
	
	/**
	 * 사이트 상세
	 * @param siteDomain
	 * @return
	 * @throws Exception
	 */
	SiteDomainVO selectSiteDomain(SiteDomainVO siteDomain) throws Exception;
	
	
}

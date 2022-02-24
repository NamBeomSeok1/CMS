package modoo.module.banner.service.impl;

import java.util.List;

import modoo.module.banner.service.BannerVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bannerMapper")
public interface BannerMapper {
	
	/**
	 * 배너 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectBannerList(BannerVO searchVO) throws Exception;
	
	/**
	 * 배너 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectBannerListCnt(BannerVO searchVO) throws Exception;
	
	/**
	 * 배너 저장
	 * @param banner
	 * @throws Exception
	 */
	void insertBanner(BannerVO banner) throws Exception;
	
	/**
	 * 배너 상세
	 * @param banner
	 * @return
	 * @throws Exception
	 */
	BannerVO selectBanner(BannerVO banner) throws Exception;
	
	/**
	 * 배너 수정
	 * @param banner
	 * @throws Exception
	 */
	void updateBanner(BannerVO banner) throws Exception;
	
	/**
	 * 배너 삭제
	 * @param banner
	 * @throws Exception
	 */
	void deleteBanner(BannerVO banner) throws Exception;
	
	//투데이스 픽
	List<?> selectTodaysPickList(BannerVO searchVO) throws Exception;
	
}

package modoo.module.banner.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.banner.service.BannerService;
import modoo.module.banner.service.BannerVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("bannerService")
public class BannerServiceImpl extends EgovAbstractServiceImpl implements BannerService {
	
	@Resource(name = "bannerMapper")
	private BannerMapper bannerMapper;
	
	@Resource(name = "bannerIdGnrService")
	private EgovIdGnrService bannerIdGnrService;

	/**
	 * 배너 목록 
	 */
	@Override
	public List<?> selectBannerList(BannerVO searchVO) throws Exception {
		return bannerMapper.selectBannerList(searchVO);
	}

	/**
	 * 배너 목록 카운트
	 */
	@Override
	public int selectBannerListCnt(BannerVO searchVO) throws Exception {
		return bannerMapper.selectBannerListCnt(searchVO);
	}

	/**
	 * 배너 저장
	 */
	@Override
	public void insertBanner(BannerVO banner) throws Exception {
		String bannerId = bannerIdGnrService.getNextStringId();
		banner.setBannerId(bannerId);
		bannerMapper.insertBanner(banner);
	}

	/**
	 * 배너 상세
	 */
	@Override
	public BannerVO selectBanner(BannerVO banner) throws Exception {
		return bannerMapper.selectBanner(banner);
	}

	/**
	 * 배너 수정
	 */
	@Override
	public void updateBanner(BannerVO banner) throws Exception {
		bannerMapper.updateBanner(banner);
	}

	/**
	 * 배너 삭제
	 */
	@Override
	public void deleteBanner(BannerVO banner) throws Exception {
		bannerMapper.deleteBanner(banner);
	}
	
	//투데이스 픽
	@Override
	public List<?> selectTodaysPickList(BannerVO searchVO) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		//일반 사용자가 아닌 회원
		if(user != null && "GROUP_00000000000000".equals(user.getGroupId())){
			searchVO.setMngAt("Y");
		//이지웰 회원이면 이지웰 회원에 맞는 상품 나오게 설정 아니면 B2C상품만 나오게 설정
		}else if(user != null && "GROUP_00000000000001".equals(user.getGroupId())){
			searchVO.setPrtnrId("PRTNR_0001");
		}else{
			searchVO.setPrtnrId("PRTNR_0000");
		}
		return bannerMapper.selectTodaysPickList(searchVO);
	}
}

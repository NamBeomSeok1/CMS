package modoo.module.shop.hdry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.hdry.service.HdryCmpnyService;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Service("hdryCmpnyService")
public class HdryCmpnyServiceImpl extends EgovAbstractServiceImpl implements HdryCmpnyService {
	
	@Resource(name = "hdryCmpnyMapper")
	private HdryCmpnyMapper hdryCmpnyMapper;
	
	@Resource(name = "hdryIdGnrService")
	private EgovIdGnrService hdryIdGnrService;

	/**
	 * 택배사목록
	 */
	@Override
	public List<HdryCmpnyVO> selectHdryCmpnyList(HdryCmpnyVO searchVO) throws Exception {
		return hdryCmpnyMapper.selectHdryCmpnyList(searchVO);
	}

	/**
	 * 택배사목록 카운트
	 */
	@Override
	public int selectHdryCmpnyListCnt(HdryCmpnyVO searchVO) throws Exception {
		return hdryCmpnyMapper.selectHdryCmpnyListCnt(searchVO);
	}

	/**
	 * 택배사저장
	 */
	@Override
	public void insertHdryCmpny(HdryCmpnyVO hdryCmpny) throws Exception {
		String hdryId = hdryIdGnrService.getNextStringId();
		hdryCmpny.setHdryId(hdryId);
		hdryCmpnyMapper.insertHdryCmpny(hdryCmpny);
	}

	/**
	 * 택배사상세
	 */
	@Override
	public HdryCmpnyVO selectHdryCmpny(HdryCmpnyVO hdryCmpny) throws Exception {
		return hdryCmpnyMapper.selectHdryCmpny(hdryCmpny);
	}

	/**
	 * 택배사수정
	 */
	@Override
	public void updateHdryCmpny(HdryCmpnyVO hdryCmpny) throws Exception {
		hdryCmpnyMapper.updateHdryCmpny(hdryCmpny);
	}

	/**
	 * 택배사삭제
	 */
	@Override
	public void deleteHdryCmpny(HdryCmpnyVO hdryCmpny) throws Exception {
		hdryCmpnyMapper.deleteHdryCmpny(hdryCmpny);
	}

	/**
	 * 택배사명 체크 카운트
	 */
	@Override
	public int selectHdryCmpnyCheckCnt(HdryCmpnyVO hdryCmpny) throws Exception {
		return hdryCmpnyMapper.selectHdryCmpnyCheckCnt(hdryCmpny);
	}
	/**
	 * 업체별 택배사 목록 조회
	 */
	@Override
	public List<HdryCmpnyVO> selectGoodsHdryList(HdryCmpnyVO hdryCmpny) {
		return hdryCmpnyMapper.selectGoodsHdryList(hdryCmpny);
	}

}

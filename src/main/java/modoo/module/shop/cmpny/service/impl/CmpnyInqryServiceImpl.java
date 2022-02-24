package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.shop.cmpny.service.CmpnyInqryService;
import modoo.module.shop.cmpny.service.CmpnyInqryVO;

@Service("cmpnyInqryService")
public class CmpnyInqryServiceImpl extends EgovAbstractServiceImpl implements CmpnyInqryService{

	@Resource(name="cmpnyInqryIdGnrService")
	private EgovIdGnrService cmpnyInqryIdGnrService;

	@Resource(name="cmpnyInqryMapper")
	private CmpnyInqryMapper cmpnyInqryMapper;
	
	
	
	/**
	 *입점문의 리스트 
	 */
	@Override
	public List<CmpnyInqryVO> selectCmpnyInqryList(CmpnyInqryVO searchVO) throws Exception {
		return cmpnyInqryMapper.selectCmpnyInqryList(searchVO);
	}
	
	/**
	 *입점문의 등록
	 */
	@Override
	public void insertCmpnyInqry(CmpnyInqryVO cmpnyInqry) throws Exception {
		String inqryId = cmpnyInqryIdGnrService.getNextStringId();
		cmpnyInqry.setInqryId(inqryId);
		cmpnyInqryMapper.insertCmpnyInqry(cmpnyInqry);
	}
	/**
	 * 입점문의 리스트 갯수
	 */
	@Override
	public int selectCmpnyInqryListCnt(CmpnyInqryVO searchVO) {
		return cmpnyInqryMapper.selectCmpnyInqryListCnt(searchVO);
	}

	/**
	 * 입점 문의 상세 조회
	 */
	@Override
	public CmpnyInqryVO selectCmpnyInqry(CmpnyInqryVO searchVO) {
		return cmpnyInqryMapper.selectCmpnyInqry(searchVO);
	}
	
	
}

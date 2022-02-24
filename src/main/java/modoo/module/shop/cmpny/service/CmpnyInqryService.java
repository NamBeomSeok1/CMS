package modoo.module.shop.cmpny.service;

import java.util.List;

public interface CmpnyInqryService{
	/**
	 *입점 문의 리스트 
	 */
	 List<CmpnyInqryVO> selectCmpnyInqryList(CmpnyInqryVO searchVO) throws Exception;

	 /**
	  *입점 문의 등록
	  */
	 void insertCmpnyInqry(CmpnyInqryVO cmpnyInqry) throws Exception;

	 /**
	  * 입점 문의 리스트 갯수
	  * @param searchVO
	  * @return
	  */
	 int selectCmpnyInqryListCnt(CmpnyInqryVO searchVO);

	 /**
	  * 입점 문의 상세 조회
	  * @param searchVO
	  * @return
	  */
	CmpnyInqryVO selectCmpnyInqry(CmpnyInqryVO searchVO);
}

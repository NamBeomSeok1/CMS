package modoo.module.popup.service.impl;

import java.util.List;

import modoo.module.popup.service.PopupVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("popupMapper")
public interface PopupMapper {
	
	/**
	 * 팝업 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectPopupList(PopupVO searchVO) throws Exception;
	
	/**
	 * 팝업 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectPopupListCnt(PopupVO searchVO) throws Exception;
	
	/**
	 * 팝업 저장
	 * @param popup
	 * @throws Exception
	 */
	void insertPopup(PopupVO popup) throws Exception;
	
	/**
	 * 팝업 상세
	 * @param popup
	 * @return
	 * @throws Exception
	 */
	PopupVO selectPopup(PopupVO popup) throws Exception;
	
	/**
	 * 팝업 수정
	 * @param popup
	 * @throws Exception
	 */
	void updatePopup(PopupVO popup) throws Exception;
	
	/**
	 * 팝업 삭제
	 * @param popup
	 * @throws Exception
	 */
	void deletePopup(PopupVO popup) throws Exception;
}

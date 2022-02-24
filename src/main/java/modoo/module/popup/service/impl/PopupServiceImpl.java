package modoo.module.popup.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.popup.service.PopupService;
import modoo.module.popup.service.PopupVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("popupService")
public class PopupServiceImpl extends EgovAbstractServiceImpl implements PopupService {
	
	@Resource(name = "popupMapper")
	private PopupMapper popupMapper;
	
	@Resource(name = "popupIdGnrService")
	private EgovIdGnrService popupIdGnrService;

	/**
	 * 팝업 목록
	 */
	@Override
	public List<?> selectPopupList(PopupVO searchVO) throws Exception {
		return popupMapper.selectPopupList(searchVO);
	}

	/**
	 * 팝업 목록 카운트
	 */
	@Override
	public int selectPopupListCnt(PopupVO searchVO) throws Exception {
		return popupMapper.selectPopupListCnt(searchVO);
	}

	/**
	 * 팝업 저장
	 */
	@Override
	public void insertPopup(PopupVO popup) throws Exception {
		String popupId = popupIdGnrService.getNextStringId();
		popup.setPopupId(popupId);
		popupMapper.insertPopup(popup);
	}

	/**
	 * 팝업 상세
	 */
	@Override
	public PopupVO selectPopup(PopupVO popup) throws Exception {
		return popupMapper.selectPopup(popup);
	}

	/**
	 * 팝업 수정
	 */
	@Override
	public void updatePopup(PopupVO popup) throws Exception {
		popupMapper.updatePopup(popup);
	}

	/**
	 * 팝업 삭제
	 */
	@Override
	public void deletePopup(PopupVO popup) throws Exception {
		popupMapper.deletePopup(popup);
	}

}

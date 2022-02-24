package modoo.module.event.service;

import java.util.List;

import modoo.module.event.service.impl.GoodsEventVO;

public interface GoodsEventService {
	/**
	 * 관리자 이벤트 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectGoodsEventList(GoodsEventVO searchVO);
	/**
	 * 이벤트 조회
	 * @param searchVO
	 * @return
	 */
	GoodsEventVO selectGoodsEvent(GoodsEventVO searchVO);
	/**
	 * 이벤트 등록
	 * @param goodsEvent
	 */
	void insertGoodsEvent(GoodsEventVO goodsEvent);
	/**
	 * 이벤트 등록 시 다음 PK 조회
	 * @return
	 */
	Integer selectNextEventNo();
	/**
	 * 이벤트 수정
	 * @param goodsEvent
	 */
	void updateGoodsEvent(GoodsEventVO goodsEvent);
	/**
	 * 프론트 > 이벤트관 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectFrontGoodsEventList(GoodsEventVO searchVO);
	/**
	 * 이벤트 삭제
	 * @param searchVO
	 */
	void deleteGoodsEvent(GoodsEventVO searchVO);
	/**
	 * 프론트 > 이벤트관 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectFrontGoodsEventListCnt(GoodsEventVO searchVO);
	/**
	 * 관리자 > 이벤트 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectGoodsEventListCnt(GoodsEventVO searchVO);
	/**
	 * 관리자 > 이벤트 이미지 삭제
	 * @param goodsEvent
	 */
	void deleteEventImg(GoodsEventVO goodsEvent);
	/**
	 * 프론트 > 메인페이지 > 이벤트 상품 목록
	 * @param goodsEvent
	 * @return
	 */
	List<?> selectMainEventList(GoodsEventVO goodsEvent);

}

package modoo.module.event.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("goodsEventMapper")
public interface GoodsEventMapper{
	
	/**
	 * 관리자 이벤트 목록
	 * @param searchVO
	 * @return
	 */
	public List<?> selectGoodsEventList(GoodsEventVO searchVO);

	/**
	 * 이벤트 조회
	 * @param searchVO
	 * @return
	 */
	public GoodsEventVO selectGoodsEvent(GoodsEventVO searchVO);

	/**
	 * 이벤트 등록
	 * @param goodsEvent
	 */
	public void insertGoodsEvent(GoodsEventVO goodsEvent);
	/**
	 * 이벤트 등록 시 다음 PK 조회
	 * @return
	 */
	public Integer selectNextEventNo();
	/**
	 * 이벤트 수정
	 * @param goodsEvent
	 */
	public void updateGoodsEvent(GoodsEventVO goodsEvent);
	/**
	 * 프론트 > 이벤트관 목록
	 * @param searchVO
	 * @return
	 */
	public List<?> selectFrontGoodsEventList(GoodsEventVO searchVO);
	/**
	 * 이벤트 삭제
	 * @param searchVO
	 */
	public void deleteGoodsEvent(GoodsEventVO searchVO);
	/**
	 * 프론트 > 이벤트관 목록 갯수
	 * @param searchVO
	 * @return
	 */
	public int selectFrontGoodsEventListCnt(GoodsEventVO searchVO);
	/**
	 * 관리자 > 이벤트 목록 갯수
	 * @param searchVO
	 * @return
	 */
	public int selectGoodsEventListCnt(GoodsEventVO searchVO);
	/**
	 * 관리자 > 이벤트 이미지 삭제
	 * @param goodsEvent
	 */
	public void deleteEventImg(GoodsEventVO goodsEvent);
	/**
	 * 프론트 > 메인페이지 > 이벤트 상품 목록
	 * @param goodsEvent
	 * @return
	 */
	public List<?> selectMainEventList(GoodsEventVO goodsEvent);

}

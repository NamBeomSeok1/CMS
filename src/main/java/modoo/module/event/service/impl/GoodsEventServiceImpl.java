package modoo.module.event.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.event.service.GoodsEventService;

@Service("goodsEventService")
public class GoodsEventServiceImpl implements GoodsEventService {
	
	@Resource(name="goodsEventMapper")
	GoodsEventMapper goodsEventMapper;

	/**
	 * 관리자 이벤트 목록
	 */
	@Override
	public List<?> selectGoodsEventList(GoodsEventVO searchVO) {
		return goodsEventMapper.selectGoodsEventList(searchVO);
	}
	
	/**
	 * 이벤트 조회
	 */
	@Override
	public GoodsEventVO selectGoodsEvent(GoodsEventVO searchVO) {
		return goodsEventMapper.selectGoodsEvent(searchVO);
	}

	/**
	 * 이벤트 등록
	 */
	@Override
	public void insertGoodsEvent(GoodsEventVO goodsEvent) {
		goodsEventMapper.insertGoodsEvent(goodsEvent);
	}

	/**
	 * 이벤트 등록 시 다음 PK 조회
	 */
	@Override
	public Integer selectNextEventNo() {
		return goodsEventMapper.selectNextEventNo();
	}
	
	/**
	 * 이벤트 수정
	 */
	@Override
	public void updateGoodsEvent(GoodsEventVO goodsEvent) {
		goodsEventMapper.updateGoodsEvent(goodsEvent);
	}
	/**
	 * 프론트 > 이벤트관 목록
	 */
	@Override
	public List<?> selectFrontGoodsEventList(GoodsEventVO searchVO) {
		return goodsEventMapper.selectFrontGoodsEventList(searchVO);
	}
	/**
	 * 이벤트 삭제
	 */
	@Override
	public void deleteGoodsEvent(GoodsEventVO searchVO) {
		goodsEventMapper.deleteGoodsEvent(searchVO);
	}
	/**
	 * 프론트 > 이벤트관 목록 갯수
	 */
	@Override
	public int selectFrontGoodsEventListCnt(GoodsEventVO searchVO) {
		return goodsEventMapper.selectFrontGoodsEventListCnt(searchVO);
	}
	/**
	 * 관리자 > 이벤트 목록 갯수
	 */
	@Override
	public int selectGoodsEventListCnt(GoodsEventVO searchVO) {
		return goodsEventMapper.selectGoodsEventListCnt(searchVO);
	}
	/**
	 * 관리자 > 이벤트 이미지 삭제
	 */
	@Override
	public void deleteEventImg(GoodsEventVO goodsEvent) {
		goodsEventMapper.deleteEventImg(goodsEvent);
	}
	/**
	 * 프론트 > 메인페이지 > 이벤트 상품 목록
	 */
	@Override
	public List<?> selectMainEventList(GoodsEventVO goodsEvent) {
		return goodsEventMapper.selectMainEventList(goodsEvent);
	}

}

package modoo.module.shop.goods.sch.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.sch.service.GoodsSearchVO;

@Mapper("goodsSearchMapper")
public interface GoodsSearchMapper {
	
	/**
	 * 상품검색 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsSearchVO> selectGoodsSearchList(GoodsSearchVO searchVO) throws Exception;
	
	/**
	 * 상품검색 저장
	 * @param goodsSearch
	 * @throws Exception
	 */
	void insertGoodsSearch(GoodsSearchVO goodsSearch) throws Exception;
	
	/**
	 * 사용자 검색어 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsSearchVO> selectUserGoodsSearchWrdList(GoodsSearchVO searchVO) throws Exception;

	/**
	 * 사용자 검색어 기록 삭제
	 * @param searchVO
	 * @throws Exception
	 */
	void deleteUserGoodsSearchList(GoodsSearchVO searchVO) throws Exception;
	
	/**
	 * 인기 검색어 카운트 목록
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<GoodsSearchVO> selectHotGoodsSearchWrdList(int day) throws Exception;
}

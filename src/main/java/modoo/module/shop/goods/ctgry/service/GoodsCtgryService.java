package modoo.module.shop.goods.ctgry.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.info.service.GoodsVO;

public interface GoodsCtgryService {
	
	/**
	 * 상품카테고리 트리 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsCtgryVO> selectGoodsCtgryTreeList(GoodsCtgryVO searchVO) throws Exception;
	
	/**
	 * 상품카테고리 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsCtgryVO> selectGoodsCtgryList(GoodsCtgryVO searchVO) throws Exception;
	
	/**
	 * 활성 상품 카테고리 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsCtgryVO> selectActGoodsCtgryList(GoodsCtgryVO searchVO) throws Exception;
	
	/**
	 * 상품카테고리 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectGoodsCtgryListCnt(GoodsCtgryVO searchVO) throws Exception;
	
	/**
	 * 상품카테고리 저장
	 * @param goodsCtgry
	 * @throws Exception
	 */
	void insertGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception;
	
	/**
	 * 상품카테고리 상세
	 * @param goodsCtgry
	 * @return
	 * @throws Exception
	 */
	GoodsCtgryVO selectGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception;
	
	/**
	 * 상푸카테고리 수정
	 * @param goodsCtgry
	 * @throws Exception
	 */
	void updateGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception;
	
	
	/**
	 * 상품케테고리 삭제
	 * @param goodsCtgry
	 * @throws Exception
	 */
	void deleteGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception;

	/**
	 * 상품카테고리 DEPTH별 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap getGoodsCtgryDepthList(GoodsCtgryVO searchVO, GoodsVO goods) throws Exception;
	
	/**
	 * 상품카테고리 메뉴 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsCtgryVO> selectGoodsCtgryMenuList(GoodsCtgryVO searchVO) throws Exception;
	/**
	 * 카테고리별 서브 카테고리 조회
	 * @param ctgry
	 * @return
	 */
	List<GoodsCtgryVO> selectSubCtgryList(GoodsCtgryVO ctgry);
}

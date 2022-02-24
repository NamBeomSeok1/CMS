package modoo.module.shop.goods.brand.service;

import java.util.List;

public interface GoodsBrandService {

	/**
	 * 상품브랜드 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsBrandVO> selectGoodsBrandList(GoodsBrandVO searchVO) throws Exception;
	
	/**
	 * 상품브랜드 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectGoodsBrandListCnt(GoodsBrandVO searchVO) throws Exception;
	
	/**
	 * 상품브랜드 저장
	 * @param goodsBrand
	 * @throws Exception
	 */
	void insertGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 상세
	 * @param goodsGrand
	 * @return
	 * @throws Exception
	 */
	GoodsBrandVO selectGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 수정
	 * @param goodsBrand
	 * @throws Exception
	 */
	void updateGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 삭제
	 * @param goodsBrand
	 * @throws Exception
	 */
	void deleteGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 메뉴목록 다시 읽기
	 * @throws Exception
	 */
	void reloadGoodsBrandGroupList() throws Exception;
	
	/**
	 * 상품브랜드 메뉴 목록
	 * @param goodsBrand
	 * @return
	 * @throws Exception
	 */
	List<GoodsBrandGroup> selectGoodsBrandMenuList(GoodsBrandVO goodsBrand) throws Exception;

	/**
	 * 브랜드관 목록 조회 (ㄱ~ㅎ, A~Z)
	 * @param searchVO
	 * @return
	 * @throws Exception 
	 */
	List<GoodsBrandVO> selectGoodsBrandByChar(GoodsBrandVO searchVO) throws Exception;
}
